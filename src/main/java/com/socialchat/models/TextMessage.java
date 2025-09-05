package com.socialchat.models;

public class TextMessage extends Message {
    private String text;

    public TextMessage(String id, String senderId, String recipientId, boolean toGroup, String text) {
        super(id, senderId, recipientId, toGroup);
        this.text = text;
    }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    @Override
    public String preview() {
        return (text.length() > 30 ? text.substring(0, 30) + "..." : text) +
                (attachments.isEmpty() ? "" : " + " + attachments.size() + " attachment(s)");
    }
}
