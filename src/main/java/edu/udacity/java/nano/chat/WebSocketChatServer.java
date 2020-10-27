package edu.udacity.java.nano.chat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

/**
 * WebSocket Server
 *
 * @see ServerEndpoint WebSocket Client
 * @see Session   WebSocket Session
 */

@Component
@ServerEndpoint(value = "/chat/{username}")
public class WebSocketChatServer {

    /**
     * All chat sessions.
     */

    private static void sendMessageToAll(Message msg) {
        WebSocketService.OnlineSessions.forEach((username, session) -> {
            synchronized (session) {
                try {
                    session.getBasicRemote().sendText(JSON.toJSONString(msg));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Open connection, 1) add session, 2) add user.
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) {

        WebSocketService.OnlineSessions.put(username, session);
        Message msg = new Message(username, "entered chat", "ENTER");
        sendMessageToAll(msg);
        Message allUsers = new Message("", "", "USERS");
        allUsers.msg =  Integer.toString(WebSocketService.OnlineSessions.size());
        sendMessageToAll(allUsers);
    }

    /**
     * Send message, 1) get username and session, 2) send message to all.
     */
    @OnMessage
    public void onMessage(Session session, String jsonStr) {
        Message messg = JSON.parseObject(jsonStr, Message.class);
        messg.type = "SPEAK";
        sendMessageToAll(messg);
    }

    /**
     * Close connection, 1) remove session, 2) update user.
     */
    @OnClose
    public void onClose(Session session, @PathParam("username") String username) {
        WebSocketService.OnlineSessions.remove(username);
        Message msg = new Message(username, "left chat", "LEAVE");
        sendMessageToAll(msg);
        Message allUsers = new Message("", "", "USERS");
        allUsers.msg =  Integer.toString(WebSocketService.OnlineSessions.size());
        sendMessageToAll(allUsers);
    }

    /**
     * Print exception.
     */
    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

}
