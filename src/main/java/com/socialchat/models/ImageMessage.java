package com.socialchat.models;

public class ImageMessage extends Message {
    private final String url;
    private final String caption;

    public ImageMessage(String id, String senderId, String recipientId, boolean toGroup, String url, String caption) {
        super(id, senderId, recipientId, toGroup);
        this.url = url;
        this.caption = caption;
    }

    @Override
    public String preview() {
        return "[Image] " + caption;
    }
}
