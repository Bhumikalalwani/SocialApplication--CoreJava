package com.socialchat.storage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import java.io.InputStream;

import com.socialchat.models.*;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class Database {
    private static Database instance;
    private String username;
    private String password;
    private static Connection connection;

    public void setCredentials(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public String getUsername() { return username; }
    public String getPassword() { return password; }

    static {
        try (InputStream input = Database.class.getClassLoader().getResourceAsStream("db.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            String url = prop.getProperty("db.url");
            String user = prop.getProperty("db.username");
            String pass = prop.getProperty("db.password");
            connection = DriverManager.getConnection(url, user, pass);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return connection;
    }
    private final Map<String, User> users = new HashMap<>();
    private final Map<String, Group> groups = new HashMap<>();
    private final Map<String, Post> posts = new HashMap<>();
    private final Map<String, Message> messages = new HashMap<>();
    private final Map<String, Attachment> attachments = new HashMap<>();
    private final Map<String, List<Story>> stories = new HashMap<>();
    public Map<String, List<Message>> directMessages() { return new HashMap<>();}
    public Map<String, List<Message>> groupMessages() { return new HashMap<>();}


    private Database() {}

    public static synchronized Database getInstance() {
        if (instance == null) instance = new Database();
        return instance;
    }

    // Original getters
    public Map<String, User> getUsers() { return users; }
    public Map<String, Group> getGroups() { return groups; }
    public Map<String, Post> getPosts() { return posts; }
    public Map<String, Message> getMessages() { return messages; }
    public Map<String, Attachment> getAttachments() { return attachments; }
    public Map<String, List<Story>> getStories() { return stories; }

    // New methods matching service calls
    public Map<String, User> users() { return users; }
    public Map<String, Group> groups() { return groups; }
    public Map<String, Post> posts() { return posts; }
    public Map<String, Message> messages() { return messages; }
    public Map<String, Attachment> attachments() { return attachments; }
    public Map<String, List<Story>> stories() { return stories; }

    // Helpers
    public void addPost(Post p) { posts.put(p.getId(), p); }
    public void addUser(User user) {
        users.put(user.getId(), user);
    }

    public void addMessage(Message message) {
        messages.put(message.getId(), message);
    }

    // Reset database
    public void reset() {
        users.clear();
        groups.clear();
        posts.clear();
        messages.clear();
        attachments.clear();
        stories.clear();
    }

    // Seed demo data
    public void seedDemoData() {
        reset();

        NormalUser alice = new NormalUser("u1", "alice", "Alice Wonderland");
        NormalUser bob = new NormalUser("u2", "bob", "Bob Builder");

        users.put(alice.getId(), alice);
        users.put(bob.getId(), bob);
    }

}
