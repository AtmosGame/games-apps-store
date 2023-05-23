package id.ac.ui.cs.advprog.gamesappsstore.controller.app;

import id.ac.ui.cs.advprog.gamesappsstore.dto.app.AppDetailFullResponse;
import id.ac.ui.cs.advprog.gamesappsstore.dto.app.AppDetailValidationRequest;
import id.ac.ui.cs.advprog.gamesappsstore.dto.app.AppDetailValidationResponse;
import id.ac.ui.cs.advprog.gamesappsstore.service.app.AppDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RestController
@RequestMapping("/app-detail")
public class AppDetailController {
    @Autowired
    private AppDetailServiceImpl appDetailService;

    @GetMapping("/{id}")
    public ResponseEntity<AppDetailFullResponse> getAppsByKeyword(@PathVariable Long id) {
        AppDetailFullResponse response = appDetailService.getAppDetailbyId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/validation")
    public ResponseEntity<AppDetailValidationResponse> validate(@ModelAttribute AppDetailValidationRequest request) {
        var response = appDetailService.validateApp(request);
        return ResponseEntity.ok(response);
    }
}
