//package id.ac.ui.cs.advprog.gamesappsstore.controller.notification;
//
//import id.ac.ui.cs.advprog.gamesappsstore.core.notification.AppDev;
//import id.ac.ui.cs.advprog.gamesappsstore.core.notification.Subscriber;
//import id.ac.ui.cs.advprog.gamesappsstore.dto.notfication.BrodcastRequest;
//import id.ac.ui.cs.advprog.gamesappsstore.dto.notfication.SubAndUnsubRequest;
//import id.ac.ui.cs.advprog.gamesappsstore.models.auth.User;
//import id.ac.ui.cs.advprog.gamesappsstore.models.auth.enums.UserRole;
//import id.ac.ui.cs.advprog.gamesappsstore.models.notification.NotificationData;
//import id.ac.ui.cs.advprog.gamesappsstore.service.notification.NotificationService;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//
//import javax.annotation.meta.When;
//import java.sql.Timestamp;
//import java.util.ArrayList;
//import java.util.List;
//
//@ExtendWith(MockitoExtension.class)
//class NotificationControllerTest {
//    @Mock
//    NotificationService notificationService;
//    @Mock
//    private SecurityContext securityContext;
//    @Mock
//    private Authentication authentication;
//    @InjectMocks
//    NotificationController notificationController;
//    User user;
//    AppDev appDev;
//    Subscriber subscriber;
//    NotificationData notificationData;
//    SubAndUnsubRequest subAndUnsubRequest;
//    BrodcastRequest brodcastRequest;
////    @BeforeEach
////    void setUp() {
////        user = User.builder()
////                .id(2)
////                .username("ehee")
////                .role(UserRole.USER)
////                .profilePicture("")
////                .active(true)
////                .build();
////
////        SecurityContextHolder.setContext(securityContext);
////
////        appDev = AppDev.builder()
////                .id(1L)
////                .appId((long) 1)
////                .subscribers(new ArrayList<>())
////                .build();
////
////        subscriber = Subscriber.builder()
////                .id(1L)
////                .userId((long) 1)
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
////
////        subAndUnsubRequest = new SubAndUnsubRequest(1L);
////        brodcastRequest = new BrodcastRequest(1L,"Terdapat update");
////    }
//
////    @Test
////    void successGetAllAppDev(){
////        List<AppDev> appDevList = new ArrayList<>();
////        appDevList.add(appDev);
////        appDev.setAppId(2L);
////        appDev.setId((2L));
////        appDevList.add(appDev);
////
////        Mockito.when(notificationService.getAllAppDeveloper()).thenReturn(appDevList);
////
////        var response = notificationController.getAllDeveloper();
////        Assertions.assertEquals(appDevList, response.getBody());
////    }
//
////    @Test
////    void successGetAllSubscriber(){
////        List<Subscriber> subscribersList = new ArrayList<>();
////        subscribersList.add(subscriber);
////        appDev.setId((2L));
////        subscribersList.add(subscriber);
////
////        Mockito.when(notificationService.getAllSubscribers()).thenReturn(subscribersList);
////
////        var response = notificationController.getAllSubscriber();
////        Assertions.assertEquals(subscribersList, response.getBody());
////    }
//
////    @Test
////    void successGetAllNotification(){
////        List<NotificationData> notificationDataList = new ArrayList<>();
////        notificationDataList.add(notificationData);
////        notificationData.setId((2L));
////        notificationDataList.add(notificationData);
////
////        Mockito.when(notificationService.getAllNotification()).thenReturn(notificationDataList);
////
////        var response = notificationController.getAllNotification();
////        Assertions.assertEquals(notificationDataList, response.getBody());
////    }
//
//    @Test
//    void successSubscribe(){
//        Mockito.when(authentication.getPrincipal()).thenReturn(user);
//        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
//        Mockito.when(notificationService.handleSubscribe
//                (subAndUnsubRequest.getAppDevId(), (long)user.getId())).thenReturn(subscriber);
//
//        var response = notificationController.subscribe(subAndUnsubRequest);
//        var expected = subscriber;
//        Assertions.assertEquals(expected, response.getBody());
//    }
//
//    @Test
//    void successUnsubscribe(){
//        Mockito.when(authentication.getPrincipal()).thenReturn(user);
//        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
//
//        var response = notificationController.unsubscribe(subAndUnsubRequest);
//        var expected = "User dengan id 2 mengunsubscribe App dengan id 1";
//        Assertions.assertEquals(expected, response.getBody());
//    }
//
////    @Test
////    void successBroadcast(){
////        Mockito.when(notificationService.handleNewBroadcast
////                (brodcastRequest.getAppDevId(), brodcastRequest.getMessage())).thenReturn(notificationData);
////
////        var response = notificationController.broadcast(brodcastRequest);
////        var expected = notificationData;
////        Assertions.assertEquals(expected, response.getBody());
////    }
//
//    @Test
//    void successNotificationById(){
//        List<NotificationData> notificationDataList = new ArrayList<>();
//        notificationDataList.add(notificationData);
//        notificationData.setId(2L);
//        notificationDataList.add(notificationData);
//
//        Mockito.when(authentication.getPrincipal()).thenReturn(user);
//        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
//        Mockito.when(notificationService.getNotificationByUserId
//                ((long)user.getId())).thenReturn(notificationDataList);
//
//        var response = notificationController.getNotificationByUserId();
//        Assertions.assertEquals(notificationDataList, response.getBody());
//    }
//
//}
