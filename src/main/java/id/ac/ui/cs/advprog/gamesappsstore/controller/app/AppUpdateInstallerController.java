package id.ac.ui.cs.advprog.gamesappsstore.controller.app;

import id.ac.ui.cs.advprog.gamesappsstore.dto.app.AppInstallerRequest;
import id.ac.ui.cs.advprog.gamesappsstore.service.app.AppInstallerUpdate;
import id.ac.ui.cs.advprog.gamesappsstore.service.verification.VerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@RestController
@RequiredArgsConstructor
public class AppUpdateInstallerController {
    private final VerificationService verificationService;
    private final AppInstallerUpdate appInstallerUpdate;

    // @Value("${file.upload-dir}")
    // private String uploadDir; // the directory to store uploaded files


    @PutMapping("/updateInstaller/{id}")
    public String updateInstaller(@PathVariable Integer id, @RequestBody AppInstallerRequest request) throws IOException {
        boolean isValid = appInstallerUpdate.validateApp(id, request);
        if (isValid) {
            return "success-page";
        }
        return "fail-page";
    }
}
