package id.ac.ui.cs.advprog.gamesappsstore.core.notification;

import id.ac.ui.cs.advprog.gamesappsstore.models.notification.NotificationData;

public interface Observer {
    void handleNotification(NotificationData notificationData);
}
