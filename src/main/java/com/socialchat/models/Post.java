package com.socialchat.models;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

public abstract class Post {
    protected final String id;
    protected final String authorId;
    protected final Instant createdAt = Instant.now();
    protected final Set<String> likes = new LinkedHashSet<>();

    protected Post(String id, String authorId) {
        this.id = id;
        this.authorId = authorId;
    }

    public String getId() { return id; }
    public String getAuthorId() { return authorId; }
    public Instant getCreatedAt() { return createdAt; }
    public Set<String> getLikes() { return likes; }

    public void like(String userId) { likes.add(userId); }
    public abstract String render();
}
