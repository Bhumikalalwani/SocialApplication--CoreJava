package com.socialchat.services;

import com.socialchat.models.MomentPost;
import com.socialchat.models.Comment;
import com.socialchat.models.Like;
import com.socialchat.storage.Database;

import java.util.*;

public class MomentService {
    private final Database db = Database.getInstance();
    private final Map<String, List<Comment>> comments = new HashMap<>();
    private final Map<String, List<Like>> likes = new HashMap<>();

    public MomentPost createMoment(String authorId, String content) {
        String id = UUID.randomUUID().toString();
        MomentPost moment = new MomentPost(id, authorId, content);
        db.addPost(moment);
        return moment;
    }

    // --- Likes ---
    public void addLike(String userId, String postId) {
        Like like = new Like(UUID.randomUUID().toString(), userId, postId);
        likes.computeIfAbsent(postId, k -> new ArrayList<>()).add(like);
    }

    public List<Like> getLikes(String postId) {
        return likes.getOrDefault(postId, Collections.emptyList());
    }

    public void removeLike(String userId, String postId) {
        likes.computeIfPresent(postId, (k, v) -> {
            v.removeIf(like -> like.getUserId().equals(userId));
            return v;
        });
    }

    // --- Comments ---
    public void addComment(String userId, String postId, String content) {
        Comment c = new Comment(UUID.randomUUID().toString(), userId, postId, content);
        comments.computeIfAbsent(postId, k -> new ArrayList<>()).add(c);
    }

    public List<Comment> getComments(String postId) {
        return comments.getOrDefault(postId, Collections.emptyList());
    }

    public void deleteComment(String commentId, String postId) {
        comments.computeIfPresent(postId, (k, v) -> {
            v.removeIf(c -> c.getId().equals(commentId));
            return v;
        });
    }

    // --- Moments ---
    public List<MomentPost> getMomentsForUser(String userId) {
        List<MomentPost> result = new ArrayList<>();
        db.getUsers().get(userId).getContacts().forEach(friendId -> {
            db.getPosts().values().stream()
                    .filter(p -> p instanceof MomentPost)  // type check only
                    .map(p -> (MomentPost) p)              // safe cast
                    .filter(mp -> mp.getAuthorId().equals(friendId)) // check authorId after cast
                    .forEach(result::add);
        });
        return result;
    }

    public void deleteMoment(String postId, String authorId) {
        var post = db.getPosts().get(postId);
        if (post != null && post instanceof MomentPost && post.getAuthorId().equals(authorId)) {
            db.getPosts().remove(postId);
            comments.remove(postId);
            likes.remove(postId);
        }
    }
}
