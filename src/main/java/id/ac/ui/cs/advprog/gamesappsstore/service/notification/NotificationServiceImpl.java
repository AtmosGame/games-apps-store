package id.ac.ui.cs.advprog.gamesappsstore.service.notification;

import id.ac.ui.cs.advprog.gamesappsstore.core.notification.AppDev;
import id.ac.ui.cs.advprog.gamesappsstore.core.notification.Subscriber;
import id.ac.ui.cs.advprog.gamesappsstore.exceptions.auth.UserNotFoundException;
import id.ac.ui.cs.advprog.gamesappsstore.exceptions.notification.AppDevDoesNotExistException;
import id.ac.ui.cs.advprog.gamesappsstore.exceptions.notification.SubscriberAlreadySubscribeExepction;
import id.ac.ui.cs.advprog.gamesappsstore.exceptions.notification.SubscriberDoesNotExistException;
import id.ac.ui.cs.advprog.gamesappsstore.models.notification.NotificationData;
import id.ac.ui.cs.advprog.gamesappsstore.repository.notification.AppDeveloperRepository;
import id.ac.ui.cs.advprog.gamesappsstore.repository.notification.NotificationDataRepository;
import id.ac.ui.cs.advprog.gamesappsstore.repository.notification.SubscriberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements  NotificationService{
    private final AppDeveloperRepository appDeveloperRepository;
    private final SubscriberRepository subscriberRepository;
    private final NotificationDataRepository notificationDataRepository;

    @Override
    public List<AppDev> getAllAppDeveloper() {
        return appDeveloperRepository.findAll();
    }

    @Override
    public List<Subscriber> getAllSubscribers() {
        return subscriberRepository.findAll();
    }

    @Override
    public List<NotificationData> getAllNotification() {
        return notificationDataRepository.findAll();
    }

    @Override
    public List<NotificationData> getNotificationByUserId(Long userId) {
        var subscriberList = subscriberRepository.findByUserId(userId);
        if (subscriberList.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }
        var subscriber = subscriberList.get(0);
        var notifications = notificationDataRepository.findBySubscriber(subscriber);
        notifications.sort(Comparator.comparing(NotificationData::getTimestamp));
        Collections.reverse(notifications);
        return notifications;
    }

    @Override
    public boolean handleIsSubscribed(Long appDevId, Long userId) {
        var dev = appDeveloperRepository.findById(appDevId);
        if (dev.isEmpty()) {
            throw new AppDevDoesNotExistException();
        }
        var appDev = dev.get();
        var subscribers = appDev.getSubscribers();
        for (Subscriber subscriber : subscribers) {
            if (subscriber.getUserId().equals(userId)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void handleNewBroadcast(Long appDevId, String message) {
        Optional<AppDev> dev = appDeveloperRepository.findByIdWithSubscribers(appDevId);
        if (dev.isPresent()) {
            AppDev appDev = dev.get();

            List<Subscriber> subscribers = appDev.getSubscribers();
            for (Subscriber subscriber: subscribers) {
                NotificationData notificationData = NotificationData.builder()
                        .subjectId(appDev.getId())
                        .description(message)
                        .timestamp(new Timestamp(System.currentTimeMillis()))
                        .subscriber(subscriber)
                        .build();
                notificationDataRepository.save(notificationData);
            }
        } else {
            throw new AppDevDoesNotExistException();
        }
    }

    @Override
    public Subscriber handleSubscribe(Long appDevId, Long userId) {
        Optional<AppDev> dev = appDeveloperRepository.findById(appDevId);
        if(dev.isPresent()){
            Optional<Subscriber> sub = subscriberRepository.findByAppDevAndUserId(dev.get(), userId);
            if(sub.isEmpty()){
                Subscriber subscriber = Subscriber
                        .builder()
                        .userId(userId)
                        .notifications(new ArrayList<>())
                        .build();
                dev.get().addSubscriber(subscriber);
                return subscriberRepository.save(subscriber);
            }
            else{
                throw new SubscriberAlreadySubscribeExepction();
            }
        }
        else{
            throw new AppDevDoesNotExistException();
        }
    }

    @Override
    public void handleUnsubscribe(Long appDevId, Long userId) {
        Optional<AppDev> dev = appDeveloperRepository.findById(appDevId);
        if(dev.isPresent()){
            Optional<Subscriber> sub = subscriberRepository.findByAppDevAndUserId(dev.get(), userId);
            if(sub.isPresent()){
                dev.get().removeSubscriber(sub.get());
                subscriberRepository.deleteById(sub.get().getId());
            }
            else{
                throw new SubscriberDoesNotExistException();
            }
        }
        else{
            throw new AppDevDoesNotExistException();
        }
    }
}
