package com.tongwii.webRtc;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * webSocket Handler
 *
 * @author Zeral
 * @date 2017-07-17
 */
public class MyWebSocketHandler extends TextWebSocketHandler {
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        //接收到客户端消息时调用
        System.out.println("text message: " + session.getId() + "-" + message.getPayload());
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
        session.sendMessage(new TextMessage("你好"));
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
    }

    @Override
    public boolean supportsPartialMessages() {
        // TODO Auto-generated method stub
        return false;
    }
}
