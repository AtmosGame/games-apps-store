package id.ac.ui.cs.advprog.gamesappsstore.controller.app;
import id.ac.ui.cs.advprog.gamesappsstore.models.app.AppData;
import id.ac.ui.cs.advprog.gamesappsstore.dto.appCRUD.AppDataRequest;
import id.ac.ui.cs.advprog.gamesappsstore.dto.appCRUD.AppImageUpdate;
import id.ac.ui.cs.advprog.gamesappsstore.dto.appCRUD.AppInstallerUpdate;
import id.ac.ui.cs.advprog.gamesappsstore.dto.appCRUD.AppProfileUpdate;
import id.ac.ui.cs.advprog.gamesappsstore.service.app.AppCRUD;
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
public class AppRegistrationController {
    private final VerificationService verificationService;
    private final AppCRUD appRegistrationService;

    // @Value("${file.upload-dir}")
    // private String uploadDir; // the directory to store uploaded files

    @GetMapping("/all")
    public ResponseEntity<List<AppData>> getAllVerifiedApps() {
        List<AppData> response = verificationService.findAllVerifiedApps();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/submit")
    public ResponseEntity<AppData> submitForm(Model model, @ModelAttribute AppDataRequest request) throws IOException {
        System.out.println(request);
        AppData response = appRegistrationService.create(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping ("/{id}")
    public ResponseEntity<AppData> updateProfile(@PathVariable Long id, Model model, @ModelAttribute AppProfileUpdate request) throws IOException {
        AppData response = appRegistrationService.updateProfile(id, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping ("/{id}")
    public ResponseEntity<AppData> getApp(@PathVariable Long id) throws IOException {
        AppData response = appRegistrationService.findById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping ("/{id}/installer")
    public ResponseEntity<AppData> updateInstaller(@PathVariable Long id, Model model, @ModelAttribute AppInstallerUpdate request) throws IOException {
        AppData response = appRegistrationService.updateInstaller(id, request);
        return ResponseEntity.ok(response);
    }
    @PutMapping ("/{id}/image")
    public ResponseEntity<AppData> updateImage(@PathVariable Long id, Model model, @ModelAttribute AppImageUpdate request) throws IOException {
        AppData response = appRegistrationService.updateImage(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<String> deleteApp(@PathVariable Long id) throws IOException {
        appRegistrationService.delete(id);
        return ResponseEntity.ok(String.format("Menghapus App dengan id %d", id));
    }
}
