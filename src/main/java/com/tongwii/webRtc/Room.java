package com.tongwii.webRtc;

import org.springframework.web.socket.WebSocketSession;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 *
 *
 * @author Zeral
 * @date 2017-07-18
 */
public enum Room {
    INSTANCE;

    private static final ConcurrentMap<Integer, Set<WebSocketSession>> rooms = new ConcurrentHashMap<>();

    public ConcurrentMap<Integer, Set<WebSocketSession>> map() {
        return rooms;
    }
}
