package id.ac.ui.cs.advprog.gamesappsstore.controller.app;

import id.ac.ui.cs.advprog.gamesappsstore.models.app.AppData;
import id.ac.ui.cs.advprog.gamesappsstore.service.app.SearchServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@Controller
@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchController {
    private SearchServiceImpl searchService;
    @GetMapping("/{keyword}")
    public ResponseEntity<List<AppData>> getAppsByKeyword(@PathVariable String keyword) {
        List<AppData> response = searchService.searchAppsByKeyword(keyword);
        return ResponseEntity.ok(response);
    }
}