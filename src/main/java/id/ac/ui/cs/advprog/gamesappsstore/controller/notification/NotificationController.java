package id.ac.ui.cs.advprog.gamesappsstore.controller.notification;

import id.ac.ui.cs.advprog.gamesappsstore.core.notification.AppDev;
import id.ac.ui.cs.advprog.gamesappsstore.core.notification.Subscriber;
import id.ac.ui.cs.advprog.gamesappsstore.dto.appCRUD.AppImageUpdate;
import id.ac.ui.cs.advprog.gamesappsstore.dto.notfication.BrodcastRequest;
import id.ac.ui.cs.advprog.gamesappsstore.dto.notfication.SubAndUnsubRequest;
import id.ac.ui.cs.advprog.gamesappsstore.models.notification.NotificationData;
import id.ac.ui.cs.advprog.gamesappsstore.service.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController
@RequiredArgsConstructor
@RequestMapping("/notification")
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping("/dummy")
    public ResponseEntity<String> pangilDUummy() {
        notificationService.dummy();
        return ResponseEntity.ok(String.format("aman gimang"));
    }
    @GetMapping("/all-appDeveloper")
    public ResponseEntity<List<AppDev>> getAllDeveloper() {
        List<AppDev> response = notificationService.getAllAppDeveloper();
        return ResponseEntity.ok(response);
    }
    @GetMapping("/all-subscriber")
    public ResponseEntity<List<Subscriber>> getAllSubscriber() {
        List<Subscriber> response = notificationService.getAllSubscribers();
        return ResponseEntity.ok(response);
    }
    @GetMapping("/all-notification")
    public ResponseEntity<List<NotificationData>> getAllNotification() {
        List<NotificationData> response = notificationService.getAllNotification();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/subscribe")
    public ResponseEntity<String> subscribe(Model model, @ModelAttribute SubAndUnsubRequest request) {
        notificationService.handleSubscribe(request.getAppDevId(), (long)1);
        return ResponseEntity.ok(String.format("aman gimang"));
    }

    @PostMapping("/unsubscribe")
    public ResponseEntity<String> unsubscribe(Model model, @ModelAttribute SubAndUnsubRequest request) {
        notificationService.handleUnsubscribe((request.getAppDevId()), (long)1);
        return ResponseEntity.ok(String.format("aman gimang"));
    }

    @PostMapping("/broadcast")
    public ResponseEntity<String> broadcast(Model model, @ModelAttribute BrodcastRequest request) {
        notificationService.handleNewBroadcast((request.getAppDevId()), request.getMessage());
        return ResponseEntity.ok(String.format("aman gimang"));
    }
}
