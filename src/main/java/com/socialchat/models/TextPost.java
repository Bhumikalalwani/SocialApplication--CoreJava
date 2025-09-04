package com.socialchat.models;

public class TextPost extends Post {
    private final String text;

    public TextPost(String id, String authorId, String text) {
        super(id, authorId);
        this.text = text;
    }

    @Override
    public String render() {
        return "[TextPost] " + text;
    }
}
