package id.ac.ui.cs.advprog.gamesappsstore.controller.apps;

import id.ac.ui.cs.advprog.gamesappsstore.dto.apps.AppDetailResponse;
import id.ac.ui.cs.advprog.gamesappsstore.service.apps.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
@RequestMapping("/apps")
public class AppController {
    @Autowired
    private AppService appService;

    @GetMapping("/{id}")
    public ResponseEntity<AppDetailResponse> getAppDetail(@PathVariable Long id) {
        AppDetailResponse response = appService.getAppDetail(id);
        return ResponseEntity.ok(response);
    }
}
