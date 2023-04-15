package id.ac.ui.cs.advprog.gamesappsstore.controller.appregistration;

import id.ac.ui.cs.advprog.gamesappsstore.core.app.AppData;
import id.ac.ui.cs.advprog.gamesappsstore.dto.AppDataRequest;
import id.ac.ui.cs.advprog.gamesappsstore.service.appregistration.AppRegistrationService;
import id.ac.ui.cs.advprog.gamesappsstore.service.verification.VerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@Controller
@RestController
@RequiredArgsConstructor
public class AppRegistrationController {
    private final VerificationService verificationService;
    private final AppRegistrationService appRegistrationService;

    // @Value("${file.upload-dir}")
    // private String uploadDir; // the directory to store uploaded files

    @GetMapping("/all")
    public ResponseEntity<List<AppData>> getAllVerifiedApps() {
        List<AppData> response = verificationService.findAllVerifiedApps();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/submit")
    public String submitForm(Model model, @ModelAttribute AppDataRequest request) throws IOException {
        boolean isValid = appRegistrationService.validateApp(request);
        if (isValid) {
            return "success-page";
        }
        return "fail-page";
    }
}
