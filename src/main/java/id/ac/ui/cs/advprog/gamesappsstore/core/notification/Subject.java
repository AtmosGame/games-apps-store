package id.ac.ui.cs.advprog.gamesappsstore.core.notification;

import id.ac.ui.cs.advprog.gamesappsstore.models.notification.NotificationData;

public interface Subject {
    void addSubscriber(Subscriber subscriber);
    void removeSubscriber(Subscriber subscriber);
    void notifySubscriber(NotificationData notificationData);
}
