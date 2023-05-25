package id.ac.ui.cs.advprog.gamesappsstore.controller.app;

import id.ac.ui.cs.advprog.gamesappsstore.models.app.AppData;
import id.ac.ui.cs.advprog.gamesappsstore.service.app.SearchServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Controller
@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3001", "https://atmos1.vercel.app/"})
public class SearchController {
    private final SearchServiceImpl searchService;
    @GetMapping("/{keyword}")
    public ResponseEntity<List<AppData>> getAppsByKeyword(@PathVariable String keyword) {
        List<AppData> response = searchService.searchAppsByKeyword(keyword);
        return ResponseEntity.ok(response);
    }
}
