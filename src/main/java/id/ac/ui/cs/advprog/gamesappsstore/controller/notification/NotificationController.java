package id.ac.ui.cs.advprog.gamesappsstore.controller.notification;

import id.ac.ui.cs.advprog.gamesappsstore.core.notification.Subscriber;
import id.ac.ui.cs.advprog.gamesappsstore.dto.notfication.IsSubscribedResponse;
import id.ac.ui.cs.advprog.gamesappsstore.dto.notfication.SubAndUnsubRequest;
import id.ac.ui.cs.advprog.gamesappsstore.models.auth.User;
import id.ac.ui.cs.advprog.gamesappsstore.models.notification.NotificationData;
import id.ac.ui.cs.advprog.gamesappsstore.service.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController
@RequiredArgsConstructor
@RequestMapping("/notification")
@CrossOrigin(origins = {"http://localhost:3001", "http://localhost:3000", "https://atmos1.vercel.app", "https://atmos1.vercel.app/"})
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping("/is-subscribed/{id}")
    @PreAuthorize("hasAuthority('notification:subscribe')")
    public ResponseEntity<IsSubscribedResponse> isSubscribed(@PathVariable Long id) {
        Integer userId = getCurrentUser().getId();
        var response = notificationService.handleIsSubscribed(id, (long) userId);
        return ResponseEntity.ok(new IsSubscribedResponse(response));
    }

    @GetMapping("/all-notification-by-id")
    @PreAuthorize("hasAuthority('notification:get_notification_by_id')")
    public ResponseEntity<List<NotificationData>> getNotificationByUserId(){
        Integer userId = getCurrentUser().getId();
        List<NotificationData> response = notificationService.getNotificationByUserId((long)userId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/subscribe")
    @PreAuthorize("hasAuthority('notification:subscribe')")
    public ResponseEntity<Subscriber> subscribe(@RequestBody SubAndUnsubRequest request) {
        Integer userId = getCurrentUser().getId();
        Subscriber response = notificationService.handleSubscribe(request.getAppDevId(), (long)userId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/unsubscribe")
    @PreAuthorize("hasAuthority('notification:unsubscribe')")
    public ResponseEntity<String> unsubscribe(@RequestBody SubAndUnsubRequest request) {
        Integer userId = getCurrentUser().getId();
        notificationService.handleUnsubscribe((request.getAppDevId()), (long)userId);
        return ResponseEntity.ok(String.format("User dengan id %d mengunsubscribe App dengan id %d", (long)userId, request.getAppDevId()));
    }

    private static User getCurrentUser() {
        return ((User) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal());
    }
}
