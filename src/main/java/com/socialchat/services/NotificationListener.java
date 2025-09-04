package com.socialchat.services;

import com.socialchat.models.NotificationEvent;

public interface NotificationListener {
    void onEvent(NotificationEvent event);
}
