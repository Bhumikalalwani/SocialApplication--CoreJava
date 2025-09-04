package com.socialchat.storage;

import com.socialchat.models.*;
import java.util.*;

/**
 * In-memory data store. Replace with JDBC/ORM later if needed.
 */
public final class Database {
    private static final Database INSTANCE = new Database();

    // Users
    private final Map<String, User> users = new HashMap<>();
    public Map<String, User> getUsers() {
        return users;
    }
    // Direct chat messages: key = conversationId (sorted "u1:u2")
    private final Map<String, List<Message>> directMessages = new HashMap<>();
    // Groups
    private final Map<String, Group> groups = new HashMap<>();
    private final Map<String, List<Message>> groupMessages = new HashMap<>();

    // Posts (timeline)
    private final Map<String, Post> posts = new LinkedHashMap<>();

    private Database() {}

    public static Database getInstance() {
        return INSTANCE;
    }

    public Map<String, User> users() { return users; }
    public Map<String, List<Message>> directMessages() { return directMessages; }
    public Map<String, Group> groups() { return groups; }
    public Map<String, List<Message>> groupMessages() { return groupMessages; }
    public Map<String, Post> posts() { return posts; }
    private final Map<String, MomentPost> moments = new HashMap<>();
    private final Map<String, List<Story>> stories = new HashMap<>();

    public Map<String, MomentPost> getMoments() { return moments; }
    public Map<String, List<Story>> getStories() { return stories; }

}
