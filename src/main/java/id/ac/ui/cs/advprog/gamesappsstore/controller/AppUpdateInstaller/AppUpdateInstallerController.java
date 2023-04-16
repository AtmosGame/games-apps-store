package id.ac.ui.cs.advprog.gamesappsstore.controller.AppUpdateInstaller;

import id.ac.ui.cs.advprog.gamesappsstore.core.app.AppData;
import id.ac.ui.cs.advprog.gamesappsstore.dto.AppDataRequest;
import id.ac.ui.cs.advprog.gamesappsstore.dto.AppInstallerRequest;
import id.ac.ui.cs.advprog.gamesappsstore.service.appregistration.AppInstallerUpdate;
import id.ac.ui.cs.advprog.gamesappsstore.service.verification.VerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

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
