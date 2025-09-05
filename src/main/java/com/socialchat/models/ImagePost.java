package com.socialchat.models;

public class ImagePost extends Post {
    private final String url;
    private final String caption;

    public ImagePost(String id, String authorId, String url, String caption) {
        super(id, authorId);
        this.url = url;
        this.caption = caption;
    }

    public String getUrl() {
        return url;
    }

    public String getCaption() {
        return caption;
    }

    @Override
    public String render() {
        return caption + " (" + url + ")";
    }

    @Override
    public String toString() {
        return "ImagePost{" +
                "id='" + getId() + '\'' +
                ", author='" + getAuthorId() + '\'' +
                ", url='" + url + '\'' +
                ", caption='" + caption + '\'' +
                '}';
    }
}
