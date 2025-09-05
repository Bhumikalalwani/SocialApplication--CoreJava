package com.socialchat.services;

import com.socialchat.models.*;
import com.socialchat.storage.Database;

import java.util.List;
import java.util.stream.Collectors;

public class SearchService {
    private final Database db = Database.getInstance();

    // 🔎 Search users
    public List<User> searchUsers(String query) {
        return db.getUsers().values().stream()
                .filter(u -> u.getUsername().toLowerCase().contains(query.toLowerCase())
                        || (u instanceof NormalUser n
                        && n.getDisplayName().toLowerCase().contains(query.toLowerCase())))
                .collect(Collectors.toList());
    }

    // 🔎 Search groups
    public List<Group> searchGroups(String query) {
        return db.getGroups().values().stream()
                .filter(g -> g.getName().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }

    // 🔎 Search posts (moments, stories, text, image)
    public List<Post> searchPosts(String query) {
        return db.getPosts().values().stream()
                .filter(p -> {
                    if (p instanceof MomentPost m)
                        return m.getContent().toLowerCase().contains(query.toLowerCase());
                    if (p instanceof StoryPost s)
                        return s.getContent().toLowerCase().contains(query.toLowerCase());
                    if (p instanceof TextPost t)
                        return t.getContent().toLowerCase().contains(query.toLowerCase());
                    if (p instanceof ImagePost i)
                        return i.getCaption().toLowerCase().contains(query.toLowerCase());
                    return false;
                })
                .collect(Collectors.toList());
    }

    // 🔎 Search text messages
    public List<Message> searchMessages(String query) {
        return db.getMessages().values().stream()
                .filter(m -> m instanceof TextMessage t &&
                        t.getText().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }

    // 📎 Search messages by attachment type
    public List<Message> searchMessagesWithAttachments(AttachmentType type) {
        return db.getMessages().values().stream()
                .filter(m -> m.getAttachments().stream().anyMatch(a -> a.getType() == type))
                .collect(Collectors.toList());
    }
}
