package com.socialchat.models;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public abstract class User {
    protected final String id;
    protected String username;
    protected String displayName;
    protected final Set<String> contacts = new HashSet<>(); // user ids

    protected User(String id, String username, String displayName) {
        this.id = id;
        this.username = username;
        this.displayName = displayName;
    }

    public String getId() { return id; }
    public String getUsername() { return username; }
    public String getDisplayName() { return displayName; }
    public Set<String> getContacts() { return contacts; }

    public void addContact(String userId) { contacts.add(userId); }
    public void removeContact(String userId) { contacts.remove(userId); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
