package com.socialchat.models;

public class ImageMessage extends Message {
    private final String imageUrl;

    public ImageMessage(String id, String senderId, String recipientId, boolean toGroup, String imageUrl) {
        super(id, senderId, recipientId, toGroup);
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() { return imageUrl; }

    @Override
    public String preview() {
        return "[Image] " + imageUrl;
    }
}
