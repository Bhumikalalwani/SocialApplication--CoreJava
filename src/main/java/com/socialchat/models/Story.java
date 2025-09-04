package com.socialchat.models;

public class Story {
    private final String id;
    private final String authorId;
    private final String content;
    private final long createdAt;

    public Story(String id, String authorId, String content) {
        this.id = id;
        this.authorId = authorId;
        this.content = content;
        this.createdAt = System.currentTimeMillis();
    }

    public boolean isExpired() {
        return System.currentTimeMillis() - createdAt > 24 * 60 * 60 * 1000; // 24h
    }

    public String getId() { return id; }
    public String getAuthorId() { return authorId; }
    public String getContent() { return content; }
    public long getCreatedAt() { return createdAt; }
}
