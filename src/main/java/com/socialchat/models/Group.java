package com.socialchat.models;

import java.util.*;

public class Group {
    private final String id;
    private String name;
    private final Set<String> members = new HashSet<>(); // user ids
    private final String ownerId;

    public Group(String id, String name, String ownerId) {
        this.id = id;
        this.name = name;
        this.ownerId = ownerId;
        this.members.add(ownerId);
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Set<String> getMembers() { return members; }
    public String getOwnerId() { return ownerId; }
}
