package com.example.websocketdemo.model;

public class ServiceMessage {
    private MessageType type;
    private ChatMessage chat;
    private DrawMessage draw;

    public enum MessageType {
        CHAT,
        DRAW
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public ChatMessage getChat() {
        return chat;
    }

    public void setChat(ChatMessage chat) {
        this.chat = chat;
    }

    public DrawMessage getdraw() {
        return draw;
    }

    public void setdraw(DrawMessage draw) {
        this.draw = draw;
    }
}
