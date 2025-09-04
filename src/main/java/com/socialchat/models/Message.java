package com.socialchat.models;

import java.time.Instant;

public abstract class Message {
    protected final String id;
    protected final String senderId;
    protected final String recipientId; // userId or groupId depending on channel
    protected final Instant createdAt = Instant.now();
    protected final boolean toGroup;

    protected Message(String id, String senderId, String recipientId, boolean toGroup) {
        this.id = id;
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.toGroup = toGroup;
    }

    public String getId() { return id; }
    public String getSenderId() { return senderId; }
    public String getRecipientId() { return recipientId; }
    public Instant getCreatedAt() { return createdAt; }
    public boolean isToGroup() { return toGroup; }

    public abstract String preview();
}
