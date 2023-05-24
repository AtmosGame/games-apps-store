package id.ac.ui.cs.advprog.gamesappsstore.controller.app;
import id.ac.ui.cs.advprog.gamesappsstore.dto.appcrud.*;
import id.ac.ui.cs.advprog.gamesappsstore.models.app.AppData;
import id.ac.ui.cs.advprog.gamesappsstore.models.auth.User;
import id.ac.ui.cs.advprog.gamesappsstore.service.app.AppCRUD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RestController
public class AppCRUDController {
    @Autowired
    private AppCRUD appCRUD;

    @GetMapping("/all")
    public ResponseEntity<List<AppDetailResponseStatus>> getAll() {
        List<AppDetailResponseStatus> appDetailResponseStatusList = appCRUD.findAllApp();
        return ResponseEntity.ok(appDetailResponseStatusList);
    }
    @PostMapping("/submit")
    @PreAuthorize("hasAuthority('app_data:create')")
    public ResponseEntity<AppData> submitForm(@ModelAttribute AppDataRequest request) throws IOException {
        Integer userId = getCurrentUser().getId();
        AppData response = appCRUD.create(userId, request);
        return ResponseEntity.ok(response);
    }

    @PutMapping ("/{id}")
    @PreAuthorize("hasAuthority('app_data:update')")
    public ResponseEntity<AppData> updateProfile(
            @PathVariable Long id,
            @ModelAttribute AppProfileUpdate request) throws IOException {
        Integer userId = getCurrentUser().getId();
        AppData response = appCRUD.updateProfile(id, request, userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping ("/{id}")
    public ResponseEntity<AppDetailResponseStatus> getApp(@PathVariable Long id) throws IOException {
        AppData appData = appCRUD.findById(id);
        return ResponseEntity.ok(AppDetailResponseStatus.builder()
                .id(appData.getId())
                .name(appData.getName())
                .imageUrl(appData.getImageUrl())
                .description(appData.getDescription())
                .version(appData.getVersion())
                .price(appData.getPrice())
                .verificationStatus(appData.getVerificationStatus())
                .build());
    }

    @PutMapping ("/{id}/installer")
    @PreAuthorize("hasAuthority('app_data:update')")
    public ResponseEntity<AppData> updateInstaller(
            @PathVariable Long id,
            @ModelAttribute AppInstallerUpdate request) throws IOException {
        Integer userId = getCurrentUser().getId();
        AppData response = appCRUD.updateInstaller(id, request, userId);
        return ResponseEntity.ok(response);
    }

    @PutMapping ("/{id}/image")
    @PreAuthorize("hasAuthority('app_data:update')")
    public ResponseEntity<AppData> updateImage(
            @PathVariable Long id,
            @ModelAttribute AppImageUpdate request) throws IOException {
        Integer userId = getCurrentUser().getId();
        AppData response = appCRUD.updateImage(id, request, userId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping ("/{id}")
    @PreAuthorize("hasAuthority('app_data:delete')")
    public ResponseEntity<String> deleteApp(@PathVariable Long id) throws IOException {
        Integer userId = getCurrentUser().getId();
        appCRUD.delete(id, userId);
        return ResponseEntity.ok(String.format("Menghapus App dengan id %d", id));
    }

    private static User getCurrentUser() {
        return ((User) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal());
    }
}
