package com.socialchat.storage;

import com.socialchat.models.*;

import java.util.HashMap;
import java.util.Map;

public class Database {
    private static Database instance;

    private final Map<String, User> users = new HashMap<>();
    private final Map<String, Message> messages = new HashMap<>();
    private final Map<String, Post> posts = new HashMap<>();
    private final Map<String, StoryPost> stories = new HashMap<>();
    private final Map<String, MomentPost> moments = new HashMap<>();
    private final Map<String, Attachment> attachments = new HashMap<>(); // ✅ NEW

    private Database() {}

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public Map<String, User> getUsers() { return users; }
    public Map<String, Message> getMessages() { return messages; }
    public Map<String, Post> getPosts() { return posts; }
    public Map<String, StoryPost> getStories() { return stories; }
    public Map<String, MomentPost> getMoments() { return moments; }
    public Map<String, Attachment> getAttachments() { return attachments; } // ✅ NEW
}
