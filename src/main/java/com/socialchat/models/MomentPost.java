package com.socialchat.models;

import java.util.ArrayList;
import java.util.List;

public class MomentPost extends Post {
    private String content;
    private final List<String> comments = new ArrayList<>();

    public MomentPost(String id, String authorId, String content) {
        super(id, authorId);
        this.content = content;
    }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public List<String> getComments() { return comments; }
    public void addComment(String comment) { comments.add(comment); }

    @Override
    public String render() {
        return "[Moment by " + authorId + "]: " + content
                + " | Likes: " + likes.size()
                + " | Comments: " + comments.size();
    }
}
