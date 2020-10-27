package edu.udacity.java.nano.chat;

import javax.websocket.Session;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WebSocketService {
    public static Map<String, Session> OnlineSessions = new ConcurrentHashMap<>();
    public static Map<String, String> OnlineUsers = new ConcurrentHashMap<>();

}
