package id.ac.ui.cs.advprog.gamesappsstore.service.notification;

import id.ac.ui.cs.advprog.gamesappsstore.core.notification.AppDev;
import id.ac.ui.cs.advprog.gamesappsstore.core.notification.Subscriber;
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
        List<Subscriber> allSubwithUserId = subscriberRepository.findByUserId(userId);
        List<NotificationData> notificationDataList = new ArrayList<>();
        for(Subscriber subscriber : allSubwithUserId){
            notificationDataList.addAll(subscriber.getNotifications());
        }

        Collections.sort(notificationDataList, Comparator.comparing(NotificationData::getTimestamp));
        Collections.reverse(notificationDataList);
        return notificationDataList;
    }

    @Override
    public NotificationData handleNewBroadcast(Long appDevId, String message) {
        Optional<AppDev> dev = appDeveloperRepository.findByIdWithSubscribers(appDevId);
        if(dev.isPresent()){
            NotificationData notificationData = NotificationData.builder()
                    .subjectId(dev.get().getId())
                    .description(message)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .subscriber(new ArrayList<>())
                    .build();

            System.out.println("atas notifiy");
            dev.get().notifySubscriber(notificationData);
            System.out.println("bawah notifiy");
            return notificationDataRepository.save(notificationData);
        }
        else{
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
