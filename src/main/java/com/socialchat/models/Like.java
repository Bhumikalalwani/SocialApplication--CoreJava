package com.socialchat.models;

import java.time.Instant;

public class Like {
    private final String id;
    private final String userId;
    private final String postId;
    private final Instant createdAt = Instant.now();

    public Like(String id, String userId, String postId) {
        this.id = id;
        this.userId = userId;
        this.postId = postId;
    }

    public String getId() { return id; }
    public String getUserId() { return userId; }
    public String getPostId() { return postId; }
    public Instant getCreatedAt() { return createdAt; }

    @Override
    public String toString() {
        return userId + " liked at " + createdAt;
    }
}
