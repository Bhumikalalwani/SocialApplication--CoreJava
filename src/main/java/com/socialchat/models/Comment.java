package com.socialchat.models;

import java.time.Instant;

public class Comment {
    private final String id;
    private final String authorId;
    private final String postId;
    private final String content;
    private final Instant createdAt = Instant.now();

    public Comment(String id, String authorId, String postId, String content) {
        this.id = id;
        this.authorId = authorId;
        this.postId = postId;
        this.content = content;
    }

    public String getId() { return id; }
    public String getAuthorId() { return authorId; }
    public String getPostId() { return postId; }
    public String getContent() { return content; }
    public Instant getCreatedAt() { return createdAt; }

    @Override
    public String toString() {
        return "[" + createdAt + "] " + authorId + ": " + content;
    }
}
