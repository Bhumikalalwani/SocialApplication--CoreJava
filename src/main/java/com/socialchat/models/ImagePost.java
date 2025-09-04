package com.socialchat.models;

public class ImagePost extends Post {
    private final String url;
    private final String caption;

    public ImagePost(String id, String authorId, String url, String caption) {
        super(id, authorId);
        this.url = url;
        this.caption = caption;
    }

    @Override
    public String render() {
        return "[ImagePost] " + caption + " (" + url + ")";
    }
}
