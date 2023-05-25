package id.ac.ui.cs.advprog.gamesappsstore.service.notification;

import id.ac.ui.cs.advprog.gamesappsstore.core.notification.AppDev;
import id.ac.ui.cs.advprog.gamesappsstore.core.notification.Subscriber;
import id.ac.ui.cs.advprog.gamesappsstore.models.notification.NotificationData;

import java.util.List;

public interface NotificationService {
    List<AppDev> getAllAppDeveloper();
    List<Subscriber> getAllSubscribers();
    List<NotificationData> getAllNotification();
    List<NotificationData> getNotificationByUserId(Long userId);
    void handleNewBroadcast(Long appDevId, String message);
    Subscriber handleSubscribe(Long appDevId, Long userId);
    void handleUnsubscribe(Long appDevId, Long userId);
}
