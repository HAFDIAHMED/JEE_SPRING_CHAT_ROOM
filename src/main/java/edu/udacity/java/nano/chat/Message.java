package edu.udacity.java.nano.chat;

/**
 * WebSocket message model
 */
public class Message {
    public String username;
    public String msg;
    public String type;

    public Message(){

    }

    public  Message(String username, String msg, String type) {
        this.username = username;
        this.msg = msg;
        this.type = type;
    }

    public Message(String username, String msg) {
        this.username = username;
        this.msg = msg;
    }
}
