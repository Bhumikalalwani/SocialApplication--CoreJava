package com.socialchat;
import com.socialchat.sql.*;
import com.socialchat.models.*;
import com.socialchat.services.SearchService;
import com.socialchat.storage.Database;

import java.util.Scanner;
import java.util.UUID;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Database db = Database.getInstance();
    private static final SearchService searchService = new SearchService();
    private static void initializeDatabaseCredentials() {
        System.out.print("Enter DB username: ");
        String username = scanner.nextLine();
        System.out.print("Enter DB password: ");
        String password = scanner.nextLine();
        db.setCredentials(username, password);
    }

    public static void main(String[] args) {
        promptUserNames();
        seedData(); // preload some users, posts, messages
        showMenu();

    }

    private static void promptUserNames() {
        System.out.print("Enter first user's display name: ");
        user1Name = scanner.nextLine();
        System.out.print("Enter second user's display name: ");
        user2Name = scanner.nextLine();
    }
    private static void showMenu() {
        while (true) {
            System.out.println("\n=== SocialChat CLI ===");
            System.out.println("1. Search Users");
            System.out.println("2. Search Groups");
            System.out.println("3. Search Posts");
            System.out.println("4. Search Messages");
            System.out.println("5. Search Messages by Attachment");
            System.out.println("0. Exit");
            System.out.print("Choose option: ");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1" -> searchUsers();
                case "2" -> searchGroups();
                case "3" -> searchPosts();
                case "4" -> searchMessages();
                case "5" -> searchMessagesByAttachment();
                case "0" -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("❌ Invalid choice!");
            }
        }
    }

    private static void searchUsers() {
        System.out.print("Enter query: ");
        String query = scanner.nextLine();
        searchService.searchUsers(query).forEach(System.out::println);
    }

    private static void searchGroups() {
        System.out.print("Enter query: ");
        String query = scanner.nextLine();
        searchService.searchGroups(query).forEach(System.out::println);
    }

    private static void searchPosts() {
        System.out.print("Enter query: ");
        String query = scanner.nextLine();
        searchService.searchPosts(query).forEach(System.out::println);
    }

    private static void searchMessages() {
        System.out.print("Enter query: ");
        String query = scanner.nextLine();
        searchService.searchMessages(query).forEach(System.out::println);
    }

    private static void searchMessagesByAttachment() {
        System.out.print("Enter attachment type (IMAGE, VIDEO, FILE): ");
        String type = scanner.nextLine();
        try {
            AttachmentType at = AttachmentType.valueOf(type.toUpperCase());
            searchService.searchMessagesWithAttachments(at).forEach(System.out::println);
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Invalid attachment type!");
        }
    }

    // Seed sample data for testing


    private static void seedData() {
        User user1 = new NormalUser(user1Id, user1Name.toLowerCase(), user1Name);
        User user2 = new NormalUser(user2Id, user2Name.toLowerCase(), user2Name);

        db.addUser(user1);
        db.addUser(user2);

        Post p1 = new TextPost(UUID.randomUUID().toString(), "u1", "Hello from Alice!");
        Post p2 = new ImagePost(UUID.randomUUID().toString(), "u2",
                "http://img.com/cat.png", "Bob’s cat picture");

        db.addPost(p1);
        db.addPost(p2);

Message m1 = new TextMessage(
    UUID.randomUUID().toString(),
    "u1",
    "u2",
    false, // toGroup
    "Hey Bob!"
);
        db.addMessage(m1);
Message m2 = new ImageMessage(
    UUID.randomUUID().toString(),
    "u2",
    "u1",
    false, // toGroup
    "http://img.com/dog.png"
);
db.addMessage(m2);
    }
}
