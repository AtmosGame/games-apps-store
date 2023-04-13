package id.ac.ui.cs.advprog.gamesappsstore.controller.AppRegistration;

import id.ac.ui.cs.advprog.gamesappsstore.dto.AppDataRequest;
import id.ac.ui.cs.advprog.gamesappsstore.service.AppRegistration.AppRegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Controller
@RestController
@RequiredArgsConstructor
public class AppRegistrationController {
    private final AppRegistrationService appRegistrationService;

    @Value("${file.upload-dir}")
    private String uploadDir; // the directory to store uploaded files

    @PostMapping("/submit")
    public String submitForm(Model model, @ModelAttribute AppDataRequest request) throws IOException {
        boolean isValid = appRegistrationService.validateApp(request);
        if (isValid) {
            return "success-page";
        }
        return "fail-page";
    }
}
