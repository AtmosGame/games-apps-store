package id.ac.ui.cs.advprog.gamesappsstore.controller;

import id.ac.ui.cs.advprog.gamesappsstore.models.AppData;
import id.ac.ui.cs.advprog.gamesappsstore.service.AppRegistrationService;
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
        AppData appData = new AppData(appName, imageFile.getName(), description, installerFile.getName(), version, price);
        appRegistrationService.validateApp(appData);
        // Return the name of the success page
        return "success-page";
    }

}
