package id.ac.ui.cs.advprog.gamesappsstore.controller.notification;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import id.ac.ui.cs.advprog.gamesappsstore.core.notification.AppDev;
import id.ac.ui.cs.advprog.gamesappsstore.core.notification.Subscriber;
import id.ac.ui.cs.advprog.gamesappsstore.dto.notfication.BrodcastRequest;
import id.ac.ui.cs.advprog.gamesappsstore.dto.notfication.IsSubscribedResponse;
import id.ac.ui.cs.advprog.gamesappsstore.dto.notfication.SubAndUnsubRequest;
import id.ac.ui.cs.advprog.gamesappsstore.exceptions.advice.GlobalExceptionHandler;
import id.ac.ui.cs.advprog.gamesappsstore.exceptions.crudapp.PriceRangeException;
import id.ac.ui.cs.advprog.gamesappsstore.models.auth.User;
import id.ac.ui.cs.advprog.gamesappsstore.models.auth.enums.UserRole;
import id.ac.ui.cs.advprog.gamesappsstore.models.notification.NotificationData;
import id.ac.ui.cs.advprog.gamesappsstore.service.notification.NotificationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.WebRequest;

import java.sql.Timestamp;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class NotificationControllerTest {
    @Mock
    private WebRequest webRequest;

    @Mock
    NotificationService notificationService;
    @Mock
    private SecurityContext securityContext;
    @Mock
    private Authentication authentication;
    @InjectMocks
    NotificationController notificationController;
    User user;
    AppDev appDev;
    Subscriber subscriber;
    NotificationData notificationData;
    SubAndUnsubRequest subAndUnsubRequest;
    BrodcastRequest brodcastRequest;
    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(2)
                .username("ehee")
                .role(UserRole.USER)
                .profilePicture("")
                .active(true)
                .build();

        SecurityContextHolder.setContext(securityContext);

        appDev = AppDev.builder()
                .id(1L)
                .appId((long) 1)
                .subscribers(new ArrayList<>())
                .build();

        subscriber = Subscriber.builder()
                .id(1L)
                .userId((long) 1)
                .appDev(null)
                .notifications(new ArrayList<>())
                .build();

        notificationData = NotificationData.builder()
                .id(1L)
                .subjectId(1L)
                .description("Terdapat Update pada Aplikasi ANDA")
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();

        subAndUnsubRequest = new SubAndUnsubRequest(1L);
        brodcastRequest = new BrodcastRequest(1L,"Terdapat update");
    }

    @Test
    void successSubscribe(){
        Mockito.when(authentication.getPrincipal()).thenReturn(user);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(notificationService.handleSubscribe
                (subAndUnsubRequest.getAppDevId(), (long)user.getId())).thenReturn(subscriber);

        var response = notificationController.subscribe(subAndUnsubRequest);
        var expected = subscriber;
        Assertions.assertEquals(expected, response.getBody());
    }

    @Test
    void handleTypeMismatchTest() {
        GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();

        HttpMessageNotReadableException exception = new HttpMessageNotReadableException("Invalid request body");

        ResponseEntity<Object> responseEntity = exceptionHandler.handleTypeMismatch(exception, webRequest);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        String expectedMessage = "Format input tidak valid. Pastikan semua bidang input berjenis yang benar.";
        String actualMessage = ((Map<String, Object>) responseEntity.getBody()).get("message").toString();
        Assertions.assertEquals(expectedMessage, actualMessage);

    }
    @Test
    void unsubOrSubStringIdInvalidFormat(){
        String jsonResponse = "{\n" +
                "    \"appDevId\" : \"sadsadsa\"\n" +
                "}";
        ObjectMapper objectMapper = new ObjectMapper();

        Assertions.assertThrows(InvalidFormatException.class, () -> {
            notificationController.subscribe(objectMapper.readValue(jsonResponse, SubAndUnsubRequest.class));
        });
    }

    @Test
    void successUnsubscribe(){
        Mockito.when(authentication.getPrincipal()).thenReturn(user);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);

        var response = notificationController.unsubscribe(subAndUnsubRequest);
        var expected = "User dengan id 2 mengunsubscribe App dengan id 1";
        Assertions.assertEquals(expected, response.getBody());
    }

    @Test
    void isSubscribedTest(){
        Mockito.when(authentication.getPrincipal()).thenReturn(user);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(notificationService.handleIsSubscribed
                (any(Long.class), any(Long.class))).thenReturn(true);

        var response = notificationController.isSubscribed(1L);
        Assertions.assertEquals(new IsSubscribedResponse(true), response.getBody());


    }

    @Test
    void successNotificationById(){
        List<NotificationData> notificationDataList = new ArrayList<>();
        notificationDataList.add(notificationData);
        notificationData.setId(2L);
        notificationDataList.add(notificationData);

        Mockito.when(authentication.getPrincipal()).thenReturn(user);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(notificationService.getNotificationByUserId
                ((long)user.getId())).thenReturn(notificationDataList);

        var response = notificationController.getNotificationByUserId();
        Assertions.assertEquals(notificationDataList, response.getBody());
    }

}
