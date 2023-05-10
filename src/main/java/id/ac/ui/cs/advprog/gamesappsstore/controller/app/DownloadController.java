package id.ac.ui.cs.advprog.gamesappsstore.controller.app;

import id.ac.ui.cs.advprog.gamesappsstore.dto.download.AppDownloadResponse;
import id.ac.ui.cs.advprog.gamesappsstore.service.app.AppDownloadServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import static id.ac.ui.cs.advprog.gamesappsstore.utils.ControllerUtils.getCurrentUser;

@Controller
@RestController
@RequestMapping("/download")
@RequiredArgsConstructor
public class DownloadController {
    private final AppDownloadServiceImpl appDownloadService;

    @GetMapping("/{id}")
    public ResponseEntity<AppDownloadResponse> getDownloadUrl(
            @PathVariable Long id,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String jwtToken) {
        String username = getCurrentUser().getUsername();
        AppDownloadResponse response = appDownloadService.getDownloadUrl(username ,id, jwtToken);
        return ResponseEntity.ok(response);
    }

}
