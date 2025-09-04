package com.socialchat.services;

import com.socialchat.models.*;
import com.socialchat.storage.Database;
import com.socialchat.utils.IdGenerator;

import java.util.Optional;

public class UserService {
    private final Database db = Database.getInstance();

    public NormalUser registerNormal(String username, String displayName) {
        String id = IdGenerator.newId();
        NormalUser u = new NormalUser(id, username, displayName);
        db.users().put(id, u);
        return u;
    }

    public BusinessUser registerBusiness(String username, String displayName, String biz) {
        String id = IdGenerator.newId();
        BusinessUser u = new BusinessUser(id, username, displayName, biz);
        db.users().put(id, u);
        return u;
    }

    public Optional<User> findById(String id) {
        return Optional.ofNullable(db.users().get(id));
    }

    public void addContact(String userId, String contactId) {
        User u = db.users().get(userId);
        if (u == null) throw new IllegalArgumentException("User not found: " + userId);
        u.addContact(contactId);
    }
}
