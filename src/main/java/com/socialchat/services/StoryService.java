package com.socialchat.services;

import com.socialchat.models.Story;
import com.socialchat.storage.Database;

import java.util.*;

public class StoryService {
    private final Database db = Database.getInstance();

    // Create a story
    public Story createStory(String authorId, String content) {
        String id = UUID.randomUUID().toString();
        Story story = new Story(id, authorId, content);

        db.getStories().computeIfAbsent(authorId, k -> new ArrayList<>()).add(story);
        return story;
    }

    // Fetch active stories from user’s friends
    public List<Story> getActiveStories(String userId) {
        Set<String> friends = db.getUsers().get(userId).getContacts();
        List<Story> activeStories = new ArrayList<>();

        for (String friendId : friends) {
            List<Story> stories = db.getStories().getOrDefault(friendId, new ArrayList<>());
            for (Story story : stories) {
                if (!story.isExpired()) {
                    activeStories.add(story);
                }
            }
        }
        return activeStories;
    }

    // Cleanup expired stories (optional, can be called periodically)
    public void cleanupExpiredStories() {
        for (List<Story> stories : db.getStories().values()) {
            stories.removeIf(Story::isExpired);
        }
    }
}
