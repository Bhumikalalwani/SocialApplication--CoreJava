package com.socialchat.models;

public class NotificationEvent {
    public enum Type { MESSAGE, POST, LIKE, GROUP_INVITE }
    private final Type type;
    private final String payload;

    public NotificationEvent(Type type, String payload) {
        this.type = type;
        this.payload = payload;
    }

    public Type getType() { return type; }
    public String getPayload() { return payload; }
}
