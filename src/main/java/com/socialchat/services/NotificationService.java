package com.socialchat.services;

import com.socialchat.models.NotificationEvent;
import java.util.ArrayList;
import java.util.List;

public class NotificationService {
    private final List<NotificationListener> listeners = new ArrayList<>();

    public void subscribe(NotificationListener listener) {
        listeners.add(listener);
    }

    public void unsubscribe(NotificationListener listener) {
        listeners.remove(listener);
    }

    public void publish(NotificationEvent event) {
        for (NotificationListener l : listeners) {
            l.onEvent(event);
        }
    }
}
