package com.socialchat.services;

import com.socialchat.models.*;
import com.socialchat.storage.Database;
import com.socialchat.utils.IdGenerator;

import java.util.*;

public class ChatService {
    private final Database db = Database.getInstance();
    private final NotificationService notifications;

    public ChatService(NotificationService notifications) {
        this.notifications = notifications;
    }

    private String conversationKey(String u1, String u2) {
        if (u1.compareTo(u2) < 0) return u1 + ":" + u2;
        return u2 + ":" + u1;
    }

    public TextMessage sendDirectText(String fromUserId, String toUserId, String text) {
        String id = IdGenerator.newId();
        TextMessage msg = new TextMessage(id, fromUserId, toUserId, false, text);
        String key = conversationKey(fromUserId, toUserId);
        db.directMessages().computeIfAbsent(key, k -> new ArrayList<>()).add(msg);
        notifications.publish(new NotificationEvent(NotificationEvent.Type.MESSAGE, "DM:" + key));
        return msg;
    }

    public List<Message> getDirectHistory(String u1, String u2) {
        return db.directMessages().getOrDefault(conversationKey(u1, u2), Collections.emptyList());
    }

    // Groups
    public Group createGroup(String ownerId, String name) {
        String id = IdGenerator.newId();
        Group g = new Group(id, name, ownerId);
        db.groups().put(id, g);
        db.groupMessages().put(id, new ArrayList<>());
        return g;
    }

    public void addMember(String groupId, String userId) {
        Group g = db.groups().get(groupId);
        if (g == null) throw new IllegalArgumentException("Group not found");
        g.getMembers().add(userId);
    }

    public TextMessage sendGroupText(String fromUserId, String groupId, String text) {
        if (!db.groups().containsKey(groupId)) throw new IllegalArgumentException("Group not found");
        String id = IdGenerator.newId();
        TextMessage msg = new TextMessage(id, fromUserId, groupId, true, text);
        db.groupMessages().get(groupId).add(msg);
        notifications.publish(new NotificationEvent(NotificationEvent.Type.MESSAGE, "GROUP:" + groupId));
        return msg;
    }

    public List<Message> getGroupHistory(String groupId) {
        return db.groupMessages().getOrDefault(groupId, Collections.emptyList());
    }
    // ==== Direct message operations ====
    public boolean deleteDirectMessage(String u1, String u2, String messageId) {
        List<Message> msgs = db.directMessages().get(conversationKey(u1, u2));
        if (msgs == null) return false;
        return msgs.removeIf(m -> m.getId().equals(messageId));
    }

    public boolean editDirectMessage(String u1, String u2, String messageId, String newText) {
        List<Message> msgs = db.directMessages().get(conversationKey(u1, u2));
        if (msgs == null) return false;
        for (Message m : msgs) {
            if (m.getId().equals(messageId) && m instanceof TextMessage) {
                ((TextMessage) m).setText(newText);
                return true;
            }
        }
        return false;
    }

    // ==== Group message operations ====
    public boolean deleteGroupMessage(String groupId, String messageId) {
        List<Message> msgs = db.groupMessages().get(groupId);
        if (msgs == null) return false;
        return msgs.removeIf(m -> m.getId().equals(messageId));
    }

    public boolean editGroupMessage(String groupId, String messageId, String newText) {
        List<Message> msgs = db.groupMessages().get(groupId);
        if (msgs == null) return false;
        for (Message m : msgs) {
            if (m.getId().equals(messageId) && m instanceof TextMessage) {
                ((TextMessage) m).setText(newText);
                return true;
            }
        }
        return false;
    }
}
