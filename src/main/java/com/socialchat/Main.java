package com.socialchat;

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
        System.out.println("\n===== FEED DEMO (Moments & Stories) =====");

        MomentService momentService = new MomentService();
        StoryService storyService = new StoryService();

// Alice posts a moment
        MomentPost aliceMoment = momentService.createMoment(alice.getId(), "Beautiful sunset in Mumbai 🌇");
        System.out.println("Alice posted a moment: " + aliceMoment.render());

// Bob likes Alice’s moment
        momentService.likeMoment(aliceMoment.getId(), bob.getId());
        System.out.println("Bob liked Alice’s moment");

// Charlie comments on Alice’s moment
        momentService.commentMoment(aliceMoment.getId(), "Wow, looks amazing!");
        System.out.println("Charlie commented on Alice’s moment");

// Fetch Alice’s timeline as Bob (friend)
        System.out.println("\nBob’s Feed (Moments from friends):");
        momentService.getMomentsForUser(bob.getId())
                .forEach(mp -> System.out.println(mp.render() + " | Likes: " + mp.getLikes().size() + " | Comments: " + mp.getComments().size()));

// Bob posts a story
        Story bobStory = storyService.createStory(bob.getId(), "At the café ☕");
        System.out.println("\nBob posted a story: " + bobStory.getContent());

// Fetch Alice’s active stories from her friends
        System.out.println("\nAlice’s Active Stories (from friends):");
        storyService.getActiveStories(alice.getId())
                .forEach(st -> System.out.println("Story by " + st.getAuthorId() + ": " + st.getContent()));

    }
}
