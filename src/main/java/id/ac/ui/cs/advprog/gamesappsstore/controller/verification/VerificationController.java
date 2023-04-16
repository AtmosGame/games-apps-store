package id.ac.ui.cs.advprog.gamesappsstore.controller.verification;

import id.ac.ui.cs.advprog.gamesappsstore.core.app.AppData;
import id.ac.ui.cs.advprog.gamesappsstore.dto.StatusResponse;
import id.ac.ui.cs.advprog.gamesappsstore.service.verification.VerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController
@RequestMapping("/verification")
@RequiredArgsConstructor
public class VerificationController {
    private VerificationService verificationService;

    @GetMapping
    public ResponseEntity<List<AppData>> getAllUnverifiedApps() {
        List<AppData> response = verificationService.findAllUnverifiedApps();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/verify")
    public ResponseEntity<StatusResponse> verifyApp(@PathVariable Long id) {
        Integer adminId = 1; // TODO: placeholder
        verificationService.verify(adminId, id);
        StatusResponse response = new StatusResponse("Verified app with id " + id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/reject")
    public ResponseEntity<StatusResponse> rejectApp(@PathVariable Long id) {
        Integer adminId = 1; // TODO: placeholder
        verificationService.reject(adminId, id);
        StatusResponse response = new StatusResponse("Rejected app with id " + id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/requestReverification")
    public ResponseEntity<StatusResponse> requestReverificationApp(@PathVariable Long id) {
        verificationService.requestReverification(id);
        StatusResponse response = new StatusResponse("Requested reverification for app with id " + id);
        return ResponseEntity.ok(response);
    }
}