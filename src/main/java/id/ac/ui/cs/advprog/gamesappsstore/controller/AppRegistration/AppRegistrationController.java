package id.ac.ui.cs.advprog.gamesappsstore.controller.AppRegistration;

import id.ac.ui.cs.advprog.gamesappsstore.models.AppRegistration.AppData;
import id.ac.ui.cs.advprog.gamesappsstore.service.AppRegistration.AppRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class AppRegistrationController {

    @Autowired
    private AppRegistrationService appRegistrationService;

    @PostMapping("/submit")
    public String submitForm(Model model,
                             @RequestParam("app-name") String appName,
                             @RequestParam("image") MultipartFile imageFile,
                             @RequestParam("description") String description,
                             @RequestParam("installer") MultipartFile installerFile,
                             @RequestParam("version") String version,
                             @RequestParam("price") Double price) {
        AppData appData = new AppData(appName, description, imageFile, installerFile, version, price);
        boolean isValid = appRegistrationService.validateApp(appData);
        if (isValid) {
            return "success-page";
        }
        return "fail-page";
    }

}
