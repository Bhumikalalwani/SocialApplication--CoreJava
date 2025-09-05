package com.socialchat.models;

public class Attachment {
    private final AttachmentType type;
    private final String url; // could also be file path or metadata

    public Attachment(AttachmentType type, String url) {
        this.type = type;
        this.url = url;
    }

    public AttachmentType getType() { return type; }
    public String getUrl() { return url; }

    @Override
    public String toString() {
        return "[" + type + "] " + url;
    }
}
