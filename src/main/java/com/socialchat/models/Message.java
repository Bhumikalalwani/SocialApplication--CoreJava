package com.socialchat.models;

import java.util.ArrayList;
import java.util.List;

public abstract class Message {
    protected final String id;
    protected final String senderId;
    protected final String recipientId;
    protected final boolean toGroup;
    protected final List<Attachment> attachments = new ArrayList<>();

    protected Message(String id, String senderId, String recipientId, boolean toGroup) {
        this.id = id;
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.toGroup = toGroup;
    }

    public String getId() { return id; }
    public String getSenderId() { return senderId; }
    public String getRecipientId() { return recipientId; }
    public boolean isToGroup() { return toGroup; }

    public List<Attachment> getAttachments() { return attachments; }
    public void addAttachment(Attachment attachment) { attachments.add(attachment); }

    public abstract String preview();
}
