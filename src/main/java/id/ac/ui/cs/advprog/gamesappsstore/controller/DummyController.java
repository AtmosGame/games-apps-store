package id.ac.ui.cs.advprog.gamesappsstore.controller;

import id.ac.ui.cs.advprog.gamesappsstore.storage.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/dummy")
public class DummyController {
    @GetMapping("/test1")
    public ResponseEntity<String> test1() {
        return ResponseEntity.ok("Test1");
    }

    @GetMapping("/test2")
    public ResponseEntity<String> test2() {
        return ResponseEntity.ok("Test2");
    }
}
