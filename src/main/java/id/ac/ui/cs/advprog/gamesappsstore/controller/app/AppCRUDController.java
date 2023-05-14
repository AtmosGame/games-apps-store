package id.ac.ui.cs.advprog.gamesappsstore.controller.app;
import id.ac.ui.cs.advprog.gamesappsstore.models.app.AppData;
import id.ac.ui.cs.advprog.gamesappsstore.dto.appCRUD.AppDataRequest;
import id.ac.ui.cs.advprog.gamesappsstore.dto.appCRUD.AppImageUpdate;
import id.ac.ui.cs.advprog.gamesappsstore.dto.appCRUD.AppInstallerUpdate;
import id.ac.ui.cs.advprog.gamesappsstore.dto.appCRUD.AppProfileUpdate;
import id.ac.ui.cs.advprog.gamesappsstore.models.auth.User;
import id.ac.ui.cs.advprog.gamesappsstore.service.app.AppCRUD;
import id.ac.ui.cs.advprog.gamesappsstore.service.verification.VerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RestController
public class AppCRUDController {
    @Autowired
    private VerificationService verificationService;
    @Autowired
    private AppCRUD appRegistrationService;

    @GetMapping("/all")
    public ResponseEntity<List<AppData>> getAllVerifiedApps() {
        List<AppData> response = verificationService.findAllVerifiedApps();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/submit")
    @PreAuthorize("hasAuthority('app_data:create')")
    public ResponseEntity<AppData> submitForm(@ModelAttribute AppDataRequest request) throws IOException {
        Integer userId = getCurrentUser().getId();
        AppData response = appRegistrationService.create(userId, request);
        return ResponseEntity.ok(response);
    }

    @PutMapping ("/{id}")
    @PreAuthorize("hasAuthority('app_data:update')")
    public ResponseEntity<AppData> updateProfile(
            @PathVariable Long id,
            @ModelAttribute AppProfileUpdate request) throws IOException {
        Integer userId = getCurrentUser().getId();
        AppData response = appRegistrationService.updateProfile(id, request, userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping ("/{id}")
    public ResponseEntity<AppData> getApp(@PathVariable Long id) throws IOException {
        AppData response = appRegistrationService.findById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping ("/{id}/installer")
    @PreAuthorize("hasAuthority('app_data:update')")
    public ResponseEntity<AppData> updateInstaller(
            @PathVariable Long id,
            @ModelAttribute AppInstallerUpdate request) throws IOException {
        Integer userId = getCurrentUser().getId();
        AppData response = appRegistrationService.updateInstaller(id, request, userId);
        return ResponseEntity.ok(response);
    }

    @PutMapping ("/{id}/image")
    @PreAuthorize("hasAuthority('app_data:update')")
    public ResponseEntity<AppData> updateImage(
            @PathVariable Long id,
            @ModelAttribute AppImageUpdate request) throws IOException {
        Integer userId = getCurrentUser().getId();
        AppData response = appRegistrationService.updateImage(id, request, userId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping ("/{id}")
    @PreAuthorize("hasAuthority('app_data:delete')")
    public ResponseEntity<String> deleteApp(@PathVariable Long id) throws IOException {
        Integer userId = getCurrentUser().getId();
        appRegistrationService.delete(id, userId);
        return ResponseEntity.ok(String.format("Menghapus App dengan id %d", id));
    }

    private static User getCurrentUser() {
        return ((User) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal());
    }
}
