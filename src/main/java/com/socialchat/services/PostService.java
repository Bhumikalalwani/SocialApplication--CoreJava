package com.socialchat.services;

import com.socialchat.models.*;
import com.socialchat.storage.Database;
import com.socialchat.utils.IdGenerator;

import java.util.*;
import java.util.stream.Collectors;

public class PostService {
    private final Database db = Database.getInstance();
    private final NotificationService notifications;

    public PostService(NotificationService notifications) {
        this.notifications = notifications;
    }

    public TextPost createTextPost(String authorId, String text) {
        String id = IdGenerator.newId();
        TextPost p = new TextPost(id, authorId, text);
        db.posts().put(id, p);
        notifications.publish(new NotificationEvent(NotificationEvent.Type.POST, id));
        return p;
    }

    public ImagePost createImagePost(String authorId, String url, String caption) {
        String id = IdGenerator.newId();
        ImagePost p = new ImagePost(id, authorId, url, caption);
        db.posts().put(id, p);
        notifications.publish(new NotificationEvent(NotificationEvent.Type.POST, id));
        return p;
    }

    public void like(String postId, String userId) {
        Post p = db.posts().get(postId);
        if (p == null) throw new IllegalArgumentException("Post not found");
        p.like(userId);
        notifications.publish(new NotificationEvent(NotificationEvent.Type.LIKE, postId));
    }

    public List<Post> feedForUser(String userId, int limit) {
        return db.posts().values().stream()
                .sorted(Comparator.comparing(Post::getCreatedAt).reversed())
                .limit(limit)
                .collect(Collectors.toList());
    }
}
