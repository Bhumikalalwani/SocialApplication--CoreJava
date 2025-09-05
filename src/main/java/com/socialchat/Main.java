package com.socialchat;

import java.util.List;

import com.socialchat.models.*;
import com.socialchat.services.*;

public class Main {
    public static void main(String[] args) {
        // Setup Notification service
        NotificationService notifications = new NotificationService();
        notifications.subscribe(event -> System.out.println("NOTIFY: " + event));

        // Init services
        UserService userService = new UserService();
        ChatService chat = new ChatService(notifications);
        PostService posts = new PostService(notifications);

        // Create users
        NormalUser alice = userService.registerNormal("alice", "Alice Wonderland");
        NormalUser bob = userService.registerNormal("bob", "Bob Builder");

        // Direct message
        TextMessage msg = chat.sendDirectText(alice.getId(), bob.getId(), "Hello Bob!");
        System.out.println("Direct chat history: " + chat.getDirectHistory(alice.getId(), bob.getId()));

        // Edit message
        chat.editDirectMessage(alice.getId(), bob.getId(), msg.getId(), "Hello Bob (edited)");
        System.out.println("After edit: " + chat.getDirectHistory(alice.getId(), bob.getId()));

        // Delete message
        chat.deleteDirectMessage(alice.getId(), bob.getId(), msg.getId());
        System.out.println("After delete: " + chat.getDirectHistory(alice.getId(), bob.getId()));

        // Group chat
        Group group = chat.createGroup(alice.getId(), "Study Buddies");
        chat.addMember(group.getId(), bob.getId());
        chat.sendGroupText(alice.getId(), group.getId(), "Welcome to the group!");
        System.out.println("Group chat history: " + chat.getGroupHistory(group.getId()));

        // Post feed
        posts.createTextPost(alice.getId(), "This is my first post!");
        posts.createImagePost(bob.getId(), "http://example.com/cat.png", "cute cat");
        System.out.println("Feed: " + posts.feedForUser(alice.getId(), 10));

        System.out.println("✅ Demo complete");
//FOR POSTS STORIES AND MOMENTs
        // ---------------- FEED DEMO (Dynamic with Likes + Comments) ----------------
        System.out.println("\n===== FEED DEMO (Moments & Stories) =====");

        MomentService momentService = new MomentService();
        StoryService storyService = new StoryService();

// Every user posts a moment
        Database.getInstance().getUsers().values().forEach(user -> {
            momentService.createMoment(user.getId(), user.getUsername() + " is enjoying SocialChat 🎉");
        });

// Every user posts a story
        Database.getInstance().getUsers().values().forEach(user -> {
            storyService.createStory(user.getId(), user.getUsername() + "'s story update 🌟");
        });

// Simulate likes & comments (friends engaging with each other)
        Database.getInstance().getUsers().values().forEach(user -> {
            Database.getInstance().getUsers().values().forEach(friend -> {
                if (!friend.getId().equals(user.getId())) {
                    // friend likes user’s moment
                    Database.getInstance().getPosts().values().stream().filter(p -> p instanceof MomentPost && p.getAuthorId().equals(user.getId())).forEach(p -> {
                        momentService.likeMoment(p.getId(), friend.getId());
                        momentService.addComment(friend.getId(), p.getId(), "Nice one, " + user.getUsername() + "! 👍");
                    });
                }
            });
        });

// Now, print each user's feed
        Database.getInstance().getUsers().values().forEach(user -> {
            System.out.println("\n--- Feed for " + user.getUsername() + " ---");

            // Moments from friends
            System.out.println("Moments:");
            momentService.getMomentsForUser(user.getId()).forEach(mp -> {
                System.out.println(mp.render());

                // Show likes
                if (!mp.getLikes().isEmpty()) {
                    System.out.println("  Likes: " + mp.getLikes());
                }

                // Show comments
                var postComments = momentService.getComments(mp.getId());
                if (!postComments.isEmpty()) {
                    System.out.println("  Comments:");
                    postComments.forEach(c -> System.out.println("    " + c));
                }
            });

            // Active stories from friends
            System.out.println("Stories:");
            storyService.getActiveStories(user.getId()).forEach(st -> System.out.println("Story by " + st.getAuthorId() + ": " + st.getContent()));
        });
        // ---------------- FEED DELETE DEMO ----------------
        System.out.println("\n===== FEED DELETE DEMO =====");

        var users = Database.getInstance().getUsers().values().stream().toList();
        if (users.size() >= 2) {
            var alice = users.get(0); // just picking first two users for demo
            var bob = users.get(1);

            // Find one of Alice's moments
            var aliceMoment = Database.getInstance().getPosts().values().stream().filter(p -> p instanceof MomentPost && p.getAuthorId().equals(alice.getId())).map(p -> (MomentPost) p).findFirst().orElse(null);

            if (aliceMoment != null) {
                System.out.println("\nBefore deletion, Alice's moment:");
                System.out.println(aliceMoment.render());

                // Bob unlikes Alice’s moment
                momentService.removeLike(bob.getId(), aliceMoment.getId());

                // Remove Bob’s first comment (if any)
                var bobComments = momentService.getComments(aliceMoment.getId());
                if (!bobComments.isEmpty()) {
                    String commentId = bobComments.get(0).getId();
                    momentService.deleteComment(commentId, aliceMoment.getId());
                }

                // Alice deletes her moment entirely
                momentService.deleteMoment(aliceMoment.getId(), alice.getId());

                // Verify deletion
                var stillExists = Database.getInstance().getPosts().containsKey(aliceMoment.getId());
                System.out.println("Moment still exists after delete? " + stillExists);
            }
        }
        // ---------------- FEED TIMELINE ----------------
        System.out.println("\n===== FEED TIMELINE =====");
        users.forEach(user -> {
            System.out.println("\nFeed for " + user.getName() + ":");

            List<MomentPost> feed = momentService.getMomentsForUser(user.getId());
            if (feed.isEmpty()) {
                System.out.println("  No moments yet.");
            } else {
                feed.stream().filter(m -> Database.getInstance().getPosts().containsKey(m.getId())) // skip deleted
                        .forEach(m -> {
                            System.out.println("  " + m.render());

                            // Show likes
                            if (!m.getLikes().isEmpty()) {
                                System.out.println("    Likes: " + m.getLikes().size() + " -> " + m.getLikes());
                            }

                            // Show comments
                            var comments = momentService.getComments(m.getId());
                            if (!comments.isEmpty()) {
                                System.out.println("    Comments:");
                                comments.forEach(c -> System.out.println("      - " + c.getAuthorId() + ": " + c.getText()));
                            }
                        });
            }
        });
        // ---------------- SEARCH MESSAGES DEMO ----------------
        System.out.println("\n===== SEARCH MESSAGES DEMO =====");
        SearchService search = new SearchService();

        // Search text inside chats
        System.out.println("Search Messages 'hello': " +
                search.searchMessages("hello").stream().map(Message::preview).toList());

        // Search only images
        System.out.println("Search Messages with Images: " +
                search.searchMessagesWithAttachments("image").stream().map(Message::preview).toList());

        // ---------------- SEARCH DEMO ----------------
        System.out.println("\n===== SEARCH DEMO =====");

        // 1. Search users
        System.out.println("Search users 'alice': " + search.searchUsers("alice"));

        // 2. Search groups
        Group group = chat.createGroup(alice.getId(), "Study Buddies");
        chat.addMember(group.getId(), bob.getId());
        System.out.println("Search groups 'study': " + search.searchGroups("study"));

        // 3. Search posts
        posts.createTextPost(alice.getId(), "This is my first post!");
        posts.createImagePost(bob.getId(), "http://example.com/cat.png", "cute cat");
        System.out.println("Search posts 'cat': " +
                search.searchPosts("cat").stream().map(Post::render).toList());

        // 4. Search messages
        System.out.println("Search messages 'hello': " +
                search.searchMessages("hello").stream().map(Message::render).toList());

        // 5. Search messages with attachments (images only)
        System.out.println("Search messages with IMAGE attachments: " +
                search.searchMessagesWithAttachments(AttachmentType.IMAGE)
                        .stream().map(Message::render).toList());
    }
}


