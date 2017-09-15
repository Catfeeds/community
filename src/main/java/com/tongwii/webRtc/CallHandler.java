/*
 * (C) Copyright 2014 Kurento (http://kurento.org/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.tongwii.webRtc;

import net.sf.json.JSONObject;
import org.kurento.client.IceCandidate;
import org.kurento.client.KurentoClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Protocol handler for 1 to 1 video call communication.
 *
 * @author Boni Garcia (bgarcia@gsyc.es)
 * @author Micael Gallego (micael.gallego@gmail.com)
 * @since 4.3.1
 */
public class CallHandler extends TextWebSocketHandler {


  private static final Logger log = LoggerFactory.getLogger(CallHandler.class);

  private final ConcurrentHashMap<String, CallMediaPipeline> pipelines = new ConcurrentHashMap<>();

  @Autowired
  private KurentoClient kurento;

  @Autowired
  private UserRegistry registry;

  @Override
  public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    JSONObject jsonMessage = JSONObject.fromObject(message.getPayload());
    UserSession user = registry.getBySession(session);

    if (user != null) {
      log.debug("Incoming message from user '{}': {}", user.getUserId(), jsonMessage);
    } else {
      log.debug("Incoming message from new user: {}", jsonMessage);
    }

    switch (jsonMessage.getString("id")) {
      case "register":
        try {
          register(session, jsonMessage);
        } catch (Throwable t) {
          handleErrorResponse(t, session, "resgisterResponse");
        }
        break;
      case "call":
        try {
          call(user, jsonMessage);
        } catch (Throwable t) {
          handleErrorResponse(t, session, "callResponse");
        }
        break;
      case "incomingCallResponse":
        incomingCallResponse(user, jsonMessage);
        break;
      case "onIceCandidate": {
        JSONObject candidate = jsonMessage.getJSONObject("candidate");
        if (user != null) {
          IceCandidate cand =
              new IceCandidate(candidate.getString("candidate"), candidate.getString("sdpMid")
                  , candidate.getInt("sdpMLineIndex"));
          user.addCandidate(cand);
        }
        break;
      }
      case "stop":
        stop(session);
        break;
      default:
        break;
    }
  }

  private void handleErrorResponse(Throwable throwable, WebSocketSession session, String responseId)
      throws IOException {
    stop(session);
    log.error(throwable.getMessage(), throwable);
    JSONObject response = new JSONObject();
    response.put("id", responseId);
    response.put("response", "rejected");
    response.put("message", throwable.getMessage());
    session.sendMessage(new TextMessage(response.toString()));
  }

  private void register(WebSocketSession session, JSONObject jsonMessage) throws IOException {
    String name = jsonMessage.getString("name");

    UserSession caller = new UserSession(session, name);
    String responseMsg = "accepted";
    if (name.isEmpty()) {
      responseMsg = "rejected: empty user name";
    } else if (registry.exists(name)) {
      responseMsg = "rejected: user '" + name + "' already registered";
    } else {
      registry.register(caller);
    }

    JSONObject response = new JSONObject();
    response.put("id", "resgisterResponse");
    response.put("response", responseMsg);
    caller.sendMessage(response);
  }

  private void call(UserSession caller, JSONObject jsonMessage) throws IOException {
    String to = jsonMessage.getString("to");
    String from = jsonMessage.getString("from");
    JSONObject response = new JSONObject();

    if (registry.exists(to)) {
      caller.setSdpOffer(jsonMessage.getString("sdpOffer"));
      caller.setCallingTo(to);

      response.put("id", "incomingCall");
      response.put("from", from);

      UserSession callee = registry.getByUserId(to);
      callee.sendMessage(response);
      callee.setCallingFrom(from);
    } else {
      response.put("id", "callResponse");
      response.put("response", "rejected: user '" + to + "' is not registered");

      caller.sendMessage(response);
    }
  }

  private void incomingCallResponse(final UserSession callee, JSONObject jsonMessage)
      throws IOException {
    String callResponse = jsonMessage.getString("callResponse");
    String from = jsonMessage.getString("from");
    final UserSession calleer = registry.getByUserId(from);
    String to = calleer.getCallingTo();

    if ("accept".equals(callResponse)) {
      log.debug("Accepted call from '{}' to '{}'", from, to);

      CallMediaPipeline pipeline = null;
      try {
        pipeline = new CallMediaPipeline(kurento);
        pipelines.put(calleer.getSessionId(), pipeline);
        pipelines.put(callee.getSessionId(), pipeline);

        callee.setWebRtcEndpoint(pipeline.getCalleeWebRtcEp());
        pipeline.getCalleeWebRtcEp().addIceCandidateFoundListener(
            event ->  {
                JSONObject response = new JSONObject();
                response.put("id", "iceCandidate");
                response.put("candidate", JSONObject.fromObject(event.getCandidate()));
                try {
                  synchronized (callee.getSession()) {
                    callee.getSession().sendMessage(new TextMessage(response.toString()));
                  }
                } catch (IOException e) {
                  log.debug(e.getMessage());
                }
              }
            );

        calleer.setWebRtcEndpoint(pipeline.getCallerWebRtcEp());
        pipeline.getCallerWebRtcEp().addIceCandidateFoundListener(
           event ->  {
                JSONObject response = new JSONObject();
                response.put("id", "iceCandidate");
                response.put("candidate", JSONObject.fromObject(event.getCandidate()));
                try {
                  synchronized (calleer.getSession()) {
                    calleer.getSession().sendMessage(new TextMessage(response.toString()));
                  }
                } catch (IOException e) {
                  log.debug(e.getMessage());
                }
              }
            );

        String calleeSdpOffer = jsonMessage.getString("sdpOffer");
        String calleeSdpAnswer = pipeline.generateSdpAnswerForCallee(calleeSdpOffer);
        JSONObject startCommunication = new JSONObject();
        startCommunication.put("id", "startCommunication");
        startCommunication.put("sdpAnswer", calleeSdpAnswer);

        synchronized (callee) {
          callee.sendMessage(startCommunication);
        }

        pipeline.getCalleeWebRtcEp().gatherCandidates();

        String callerSdpOffer = registry.getByUserId(from).getSdpOffer();
        String callerSdpAnswer = pipeline.generateSdpAnswerForCaller(callerSdpOffer);
        JSONObject response = new JSONObject();
        response.put("id", "callResponse");
        response.put("response", "accepted");
        response.put("sdpAnswer", callerSdpAnswer);

        synchronized (calleer) {
          calleer.sendMessage(response);
        }

        pipeline.getCallerWebRtcEp().gatherCandidates();

      } catch (Throwable t) {
        log.error(t.getMessage(), t);

        if (pipeline != null) {
          pipeline.release();
        }

        pipelines.remove(calleer.getSessionId());
        pipelines.remove(callee.getSessionId());

        JSONObject response = new JSONObject();
        response.put("id", "callResponse");
        response.put("response", "rejected");
        calleer.sendMessage(response);

        response = new JSONObject();
        response.put("id", "stopCommunication");
        callee.sendMessage(response);
      }
    } else {
      JSONObject response = new JSONObject();
      response.put("id", "callResponse");
      response.put("response", "rejected");
      calleer.sendMessage(response);
    }
  }

  public void stop(WebSocketSession session) throws IOException {
    String sessionId = session.getId();
    if (pipelines.containsKey(sessionId)) {
      pipelines.get(sessionId).release();
      CallMediaPipeline pipeline = pipelines.remove(sessionId);
      pipeline.release();

      // Both users can stop the communication. A 'stopCommunication'
      // message will be sent to the other peer.
      UserSession stopperUser = registry.getBySession(session);
      if (stopperUser != null) {
        UserSession stoppedUser =
            (stopperUser.getCallingFrom() != null) ? registry.getByUserId(stopperUser
                .getCallingFrom()) : stopperUser.getCallingTo() != null ? registry
                    .getByUserId(stopperUser.getCallingTo()) : null;

                    if (stoppedUser != null) {
                      JSONObject message = new JSONObject();
                      message.put("id", "stopCommunication");
                      stoppedUser.sendMessage(message);
                      stoppedUser.clear();
                    }
                    stopperUser.clear();
      }

    }
  }

  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
    stop(session);
    registry.removeBySession(session);
  }

}
