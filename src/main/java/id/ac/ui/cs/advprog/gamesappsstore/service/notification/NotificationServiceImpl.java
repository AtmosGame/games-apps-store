package id.ac.ui.cs.advprog.gamesappsstore.service.notification;

import id.ac.ui.cs.advprog.gamesappsstore.core.notification.AppDev;
import id.ac.ui.cs.advprog.gamesappsstore.core.notification.Subscriber;
import id.ac.ui.cs.advprog.gamesappsstore.models.notification.NotificationData;
import id.ac.ui.cs.advprog.gamesappsstore.repository.notification.AppDeveloperRepository;
import id.ac.ui.cs.advprog.gamesappsstore.repository.notification.NotificationDataRepository;
import id.ac.ui.cs.advprog.gamesappsstore.repository.notification.SubscriberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements  NotificationService{
    private final AppDeveloperRepository appDeveloperRepository;
    private final SubscriberRepository subscriberRepository;
    private final NotificationDataRepository notificationDataRepository;

    @Override
    public void dummy(){
        AppDev dev = AppDev.builder()
                .appId((long)1)
                .build();
        appDeveloperRepository.save(dev);
//        Subscriber sub = Subscriber.builder()
//                .userId((long)1)
//                .appDev(null)
//                .build();
//        subscriberRepository.save(sub);
    }

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
        return notificationDataList;
    }

    @Override
    public void handleNewBroadcast(Long appDevId, String message) {
        Optional<AppDev> dev = appDeveloperRepository.findById(appDevId);
        if(!dev.isEmpty()){
            NotificationData notificationData = NotificationData.builder()
                    .subjectId(dev.get().getId())
                    .description(message)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .subscriber(new ArrayList<>())
                    .build();

            System.out.println("WOY BUG");
            System.out.println(notificationData.getSubscriber());
            dev.get().notifySubscriber(notificationData);
            notificationDataRepository.save(notificationData);
        }
    }

    @Override
    public void handleSubscribe(Long appDevId, Long userId) {
        Optional<AppDev> dev = appDeveloperRepository.findById(appDevId);
        if(!dev.isEmpty()){
            Optional<Subscriber> sub = subscriberRepository.findByAppDevAndUserId(dev.get(), userId);
            if(sub.isEmpty()){
                Subscriber subscriber = Subscriber
                        .builder()
                        .userId(userId)
                        .notifications(new ArrayList<>())
                        .build();
                dev.get().addSubscriber(subscriber);
                subscriberRepository.save(subscriber);
            }
        }
    }

    @Override
    public void handleUnsubscribe(Long appDevId, Long userId) {
        Optional<AppDev> dev = appDeveloperRepository.findById(appDevId);
        if(!dev.isEmpty()){
            Optional<Subscriber> sub = subscriberRepository.findByAppDevAndUserId(dev.get(), userId);
            if(!sub.isEmpty()){
                dev.get().removeSubscriber(sub.get());
                subscriberRepository.deleteById(sub.get().getId());
            }
        }
    }
}
