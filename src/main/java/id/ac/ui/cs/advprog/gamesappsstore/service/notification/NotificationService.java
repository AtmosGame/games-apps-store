package id.ac.ui.cs.advprog.gamesappsstore.service.notification;

import id.ac.ui.cs.advprog.gamesappsstore.core.notification.Subscriber;
import id.ac.ui.cs.advprog.gamesappsstore.models.notification.NotificationData;

import java.util.List;

public interface NotificationService {
    List<NotificationData> getNotificationByUserId(Long userId);
    void handleNewBroadcast(Long appDevId, String message);
    boolean handleIsSubscribed(Long appDevId, Long userId);
    Subscriber handleSubscribe(Long appDevId, Long userId);
    void handleUnsubscribe(Long appDevId, Long userId);
}
