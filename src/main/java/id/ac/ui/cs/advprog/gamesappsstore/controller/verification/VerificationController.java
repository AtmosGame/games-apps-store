package id.ac.ui.cs.advprog.gamesappsstore.controller.verification;

import id.ac.ui.cs.advprog.gamesappsstore.models.app.AppData;
import id.ac.ui.cs.advprog.gamesappsstore.dto.StatusResponse;
import id.ac.ui.cs.advprog.gamesappsstore.dto.verification.VerificationDetailResponse;
import id.ac.ui.cs.advprog.gamesappsstore.models.auth.User;
import id.ac.ui.cs.advprog.gamesappsstore.service.verification.VerificationService;
import id.ac.ui.cs.advprog.gamesappsstore.utils.ControllerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController
@RequestMapping("/verification")
public class VerificationController {
    @Autowired
    private VerificationService verificationService;

    @GetMapping("/all")
    public ResponseEntity<List<AppData>> getAllVerifiedApps() {
        List<AppData> response = verificationService.findAllVerifiedApps();
        return ResponseEntity.ok(response);
    }
    @GetMapping
    public ResponseEntity<List<AppData>> getAllUnverifiedApps() {
        List<AppData> response = verificationService.findAllUnverifiedApps();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('verification:read')")
    public ResponseEntity<VerificationDetailResponse> getAppDetail(@PathVariable Long id) {
        VerificationDetailResponse response = verificationService.getAppDetail(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/verify")
    @PreAuthorize("hasAuthority('verification:verify')")
    public ResponseEntity<StatusResponse> verifyApp(@PathVariable Long id) {
        User admin = ControllerUtils.getCurrentUser();
        verificationService.verify(admin, id);
        StatusResponse response = new StatusResponse("Verified app with id " + id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/reject")
    @PreAuthorize("hasAuthority('verification:reject')")
    public ResponseEntity<StatusResponse> rejectApp(@PathVariable Long id) {
        User admin = ControllerUtils.getCurrentUser();
        verificationService.reject(admin, id);
        StatusResponse response = new StatusResponse("Rejected app with id " + id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/requestReverification")
    @PreAuthorize("hasAuthority('verification:request_reverification')")
    public ResponseEntity<StatusResponse> requestReverificationApp(@PathVariable Long id) {
        User user = ControllerUtils.getCurrentUser();
        verificationService.requestReverification(user, id);
        StatusResponse response = new StatusResponse("Requested reverification for app with id " + id);
        return ResponseEntity.ok(response);
    }
}
