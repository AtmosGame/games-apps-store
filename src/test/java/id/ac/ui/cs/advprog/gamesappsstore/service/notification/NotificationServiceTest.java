//package id.ac.ui.cs.advprog.gamesappsstore.service.notification;
//
//import id.ac.ui.cs.advprog.gamesappsstore.core.notification.AppDev;
//import id.ac.ui.cs.advprog.gamesappsstore.core.notification.Subscriber;
//import id.ac.ui.cs.advprog.gamesappsstore.exceptions.notification.AppDevDoesNotExistException;
//import id.ac.ui.cs.advprog.gamesappsstore.exceptions.notification.SubscriberAlreadySubscribeExepction;
//import id.ac.ui.cs.advprog.gamesappsstore.exceptions.notification.SubscriberDoesNotExistException;
//import id.ac.ui.cs.advprog.gamesappsstore.models.notification.NotificationData;
//import id.ac.ui.cs.advprog.gamesappsstore.repository.notification.AppDeveloperRepository;
//import id.ac.ui.cs.advprog.gamesappsstore.repository.notification.NotificationDataRepository;
//import id.ac.ui.cs.advprog.gamesappsstore.repository.notification.SubscriberRepository;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.io.IOException;
//import java.sql.Timestamp;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class NotificationServiceTest {
//    @Mock
//    AppDeveloperRepository appDeveloperRepository;
//    @Mock
//    SubscriberRepository subscriberRepository;
//    @Mock
//    NotificationDataRepository notificationDataRepository;
//
//    @InjectMocks
//    NotificationServiceImpl notificationService;
//    AppDev appDev;
//    Subscriber subscriber;
//    NotificationData notificationData;
////    @BeforeEach
////    void setUp() throws IOException {
////        appDev = AppDev.builder()
////                .id(1L)
////                .appId((long)1)
////                .subscribers(new ArrayList<>())
////                .build();
////
////        subscriber = Subscriber.builder()
////                .id(1L)
////                .userId((long)1)
////                .appDev(null)
////                .notifications(new ArrayList<>())
////                .build();
////
////        notificationData = NotificationData.builder()
////                .id(1L)
////                .subjectId(1L)
////                .description("Terdapat Update pada Aplikasi ANDA")
////                .timestamp(new Timestamp(System.currentTimeMillis()))
////                .subscriber(new ArrayList<>())
////                .build();
////    }
//
////    @Test
////    void allAppDevTest(){
////        List<AppDev> appDevList = new ArrayList<>();
////        appDevList.add(appDev);
////
////        appDev.setAppId(2L);
////        appDevList.add(appDev);
////
////        when(appDeveloperRepository.findAll()).thenReturn(appDevList);
////
////        var result = notificationService.getAllAppDeveloper();
////        Assertions.assertEquals(appDevList, result);
////
////    }
//
////    @Test
////    void allSubTest(){
////        List<Subscriber> subscriberList = new ArrayList<>();
////        subscriberList.add(subscriber);
////
////        subscriber.setId(2L);
////        subscriberList.add(subscriber);
////
////        when(subscriberRepository.findAll()).thenReturn(subscriberList);
////
////        var result = notificationService.getAllSubscribers();
////        Assertions.assertEquals(subscriberList, result);
////
////    }
//
////    @Test
////    void allNotificationTest(){
////        List<NotificationData> notificationDataList = new ArrayList<>();
////        notificationDataList.add(notificationData);
////
////        notificationData.setId(2L);
////        notificationDataList.add(notificationData);
////
////        when(notificationDataRepository.findAll()).thenReturn(notificationDataList);
////
////        var result = notificationService.getAllNotification();
////        Assertions.assertEquals(notificationDataList, result);
////    }
//
//    @Test
//    void notificationByUserIdTest(){
//        List<Subscriber> subscriberList = new ArrayList<>();
//        subscriberList.add(subscriber);
//        subscriberList.get(0).getNotifications().add(notificationData);
//
//        List<NotificationData> notificationDataList = new ArrayList<>();
//        notificationDataList.add(notificationData);
//
//
//        when(subscriberRepository.findByUserId(any(Long.class))).thenReturn(subscriberList);
//
//        var result = notificationService.getNotificationByUserId(1L);
//        Assertions.assertEquals(notificationDataList, result);
//
//    }
//
////    @Test
////    void broadcastTest(){
////        appDev.getSubscribers().add(subscriber);
////        notificationData.getSubscriber().add(subscriber);
////        subscriber.setAppDev(appDev);
////        subscriber.getNotifications().add(notificationData);
////
////        when(appDeveloperRepository.findById(any(Long.class))).thenReturn(Optional.of(appDev));
////        when(notificationDataRepository.save(any(NotificationData.class))).thenReturn(notificationData);
////        var result = notificationService.handleNewBroadcast(1L, "Terdapat Update pada Aplikasi ANDA");
////        Assertions.assertEquals(notificationData, result);
////
////    }
//
//    @Test
//    void broadcastAppDoesNotExistTest(){
//        Assertions.assertThrows(AppDevDoesNotExistException.class, () -> {
//            notificationService.handleNewBroadcast((long)1, "ada update");
//        });
//    }
//
//    @Test
//    void subscribeTest(){
//
//        when(appDeveloperRepository.findById(any(Long.class))).thenReturn(Optional.of(appDev));
//        when(subscriberRepository.save(any(Subscriber.class))).thenReturn(subscriber);
//
//
//        var result = notificationService.handleSubscribe(1L, 1L);
//
//        appDev.getSubscribers().add(subscriber);
//        subscriber.setAppDev(appDev);
//
//        Assertions.assertEquals(subscriber, result);
//
//    }
//
//    @Test
//    void subscriberAlreadyExistTest(){
//
//        when(appDeveloperRepository.findById(any(Long.class))).thenReturn(Optional.of(appDev));
//        when(subscriberRepository.findByAppDevAndUserId(any(AppDev.class), any(Long.class))).thenReturn(Optional.ofNullable(subscriber));
//
//        Assertions.assertThrows(SubscriberAlreadySubscribeExepction.class, () -> {
//            notificationService.handleSubscribe(1L, 1L);
//        });
//
//    }
//
//    @Test
//    void devToSubscribeNotExistTest(){
//        Assertions.assertThrows(AppDevDoesNotExistException.class, () -> {
//            notificationService.handleSubscribe(1L, 1L);
//        });
//
//    }
//
//    @Test
//    void unsubscribeTest(){
//        appDev.getSubscribers().add(subscriber);
//        subscriber.setAppDev(appDev);
//
//        when(appDeveloperRepository.findById(any(Long.class))).thenReturn(Optional.of(appDev));
//        when(subscriberRepository.findByAppDevAndUserId(any(AppDev.class), any(Long.class))).thenReturn(Optional.of(subscriber));
//
//        notificationService.handleUnsubscribe(1L, 1L);
//
//        verify(subscriberRepository, atLeastOnce()).deleteById(any(Long.class));
//
//    }
//
//    @Test
//    void subscriberOnUnsubDoesNotExistTest(){
//
//        when(appDeveloperRepository.findById(any(Long.class))).thenReturn(Optional.of(appDev));
//
//        Assertions.assertThrows(SubscriberDoesNotExistException.class, () -> {
//            notificationService.handleUnsubscribe(1L, 1L);
//        });
//
//    }
//
//    @Test
//    void devToUnsubscribeNotExistTest(){
//        Assertions.assertThrows(AppDevDoesNotExistException.class, () -> {
//            notificationService.handleUnsubscribe(1L, 1L);
//        });
//
//    }
//}
