package com.socialchat.services;

import com.socialchat.models.MomentPost;
import com.socialchat.storage.Database;

import java.util.*;
import java.util.stream.Collectors;

public class MomentService {
    private final Database db = Database.getInstance();

    // Create a new moment
    public MomentPost createMoment(String authorId, String content) {
        String id = UUID.randomUUID().toString();
        MomentPost post = new MomentPost(id, authorId, content);
        db.getMoments().put(id, post);
        return post;
    }

    // Edit moment
    public boolean editMoment(String postId, String newContent) {
        MomentPost post = db.getMoments().get(postId);
        if (post != null) {
            post.setContent(newContent);
            return true;
        }
        return false;
    }

    // Delete moment
    public boolean deleteMoment(String postId) {
        return db.getMoments().remove(postId) != null;
    }

    // Like a moment
    public boolean likeMoment(String postId, String userId) {
        MomentPost post = db.getMoments().get(postId);
        if (post != null) {
            post.like(userId);
            return true;
        }
        return false;
    }

    // Comment on a moment
    public boolean commentMoment(String postId, String comment) {
        MomentPost post = db.getMoments().get(postId);
        if (post != null) {
            post.comment(comment);
            return true;
        }
        return false;
    }

    // Fetch all moments from a user’s friends
    public List<MomentPost> getMomentsForUser(String userId) {
        Set<String> friends = db.getUsers().get(userId).getContacts();
        return db.getMoments().values().stream()
                .filter(post -> friends.contains(post.getAuthorId()))
                .collect(Collectors.toList());
    }
}
