package com.tongwii.webRtc;

import net.sf.json.JSONObject;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArraySet;

import static java.util.Collections.emptySet;

/**
 * webSocket Handler
 *
 * @author Zeral
 * @date 2017-07-17
 */
public class MyWebSocketHandler extends TextWebSocketHandler {
    private ConcurrentMap<Integer, Set<WebSocketSession>> rooms = Room.INSTANCE.map();

    private static final Set<WebSocketSession> EMPTY_ROOM = emptySet();

    private int room;

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        //接收到客户端消息时调用
        System.out.println("text message: " + session.getId() + "-" + message.getPayload());
        JSONObject object = JSONObject.fromObject(message.getPayload());
        String msgType = object.getString("type");
        switch (msgType) {
            case "ENTERROOM":
                room = object.getInt("value");
                System.out.println("New client entered room " + room);
                rooms.getOrDefault(room, EMPTY_ROOM).add(session);
                break;
            default:
                room = object.getInt("value");
                rooms.getOrDefault("room", EMPTY_ROOM).parallelStream()
                        .filter(s -> s != session && s.isOpen())
                        .forEach(s -> {
                            try {
                                s.sendMessage(message);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
                break;
        }

    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session)
            throws Exception {
        // 与客户端完成连接后调用
        System.out.println("afterConnectionEstablished");
        System.out.println("getId:" + session.getId());
        System.out.println("getLocalAddress:" + session.getLocalAddress().toString());
        System.out.println("getTextMessageSizeLimit:" + session.getTextMessageSizeLimit());
        System.out.println("getUri:" + session.getUri().toString());
        System.out.println("getPrincipal:" + session.getPrincipal());
        room = new Random(System.currentTimeMillis()).nextInt(500);
        rooms.computeIfAbsent(room, s -> new CopyOnWriteArraySet<>()).add(session);
        JSONObject result = new JSONObject();
        result.put("type", "GETROOM");
        result.put("value", room);
        session.sendMessage(new TextMessage(result.toString()));
        System.out.println("Generated new room: " + room);
    }

    @Override
    public void handleTransportError(WebSocketSession session,
                                     Throwable exception) throws Exception {
        // 消息传输出错时调用
        System.out.println("handleTransportError");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session,
                                      CloseStatus closeStatus) throws Exception {
        // 一个客户端连接断开时关闭
        System.out.println("afterConnectionClosed");
        Optional.ofNullable(rooms.get(room)).orElseThrow(() -> new IllegalStateException("找不到房间" + room))
                .remove(room);
    }

}
