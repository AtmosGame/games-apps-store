package id.ac.ui.cs.advprog.gamesappsstore.controller.appdetail;

import id.ac.ui.cs.advprog.gamesappsstore.dto.AppDetailResponse;
import id.ac.ui.cs.advprog.gamesappsstore.service.detail.AppDetailService;
import id.ac.ui.cs.advprog.gamesappsstore.service.detail.AppDetailServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
@RequestMapping("/app-detail")
@RequiredArgsConstructor
public class AppDetailController {
    private AppDetailServiceImpl appDetailService;
    @GetMapping("/{id}")
    public ResponseEntity<AppDetailResponse> getAppsByKeyword(@PathVariable Long id) {
        AppDetailResponse response = appDetailService.getAppDetailbyId(id);
        return ResponseEntity.ok(response);
    }
}
