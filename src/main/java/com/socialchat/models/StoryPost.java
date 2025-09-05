package com.socialchat.models;

public class StoryPost extends Post {
    private final String content;

    public StoryPost(String id, String authorId, String content) {
        super(id, authorId);
        this.content = content;
    }

    public String getContent() { return content; }

    @Override
    public String render() {
        return "[Story] " + content + " (by " + authorId + ")";
    }
}
