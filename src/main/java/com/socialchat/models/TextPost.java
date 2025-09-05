package com.socialchat.models;

public class TextPost extends Post {
    private final String text;

    public TextPost(String id, String authorId, String text) {
        super(id, authorId);
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public String render() {
        return text;
    }

    @Override
    public String toString() {
        return "TextPost{" +
                "id='" + getId() + '\'' +
                ", author='" + getAuthorId() + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
