package id.ac.ui.cs.advprog.gamesappsstore.service.verification;

import id.ac.ui.cs.advprog.gamesappsstore.exceptions.UnauthorizedException;
import id.ac.ui.cs.advprog.gamesappsstore.exceptions.crudapp.AppDataDoesNotExistException;
import id.ac.ui.cs.advprog.gamesappsstore.models.app.AppData;
import id.ac.ui.cs.advprog.gamesappsstore.models.auth.User;
import id.ac.ui.cs.advprog.gamesappsstore.core.verification.AppDataVerification;
import id.ac.ui.cs.advprog.gamesappsstore.dto.verification.VerificationDetailResponse;
import id.ac.ui.cs.advprog.gamesappsstore.models.app.enums.VerificationStatus;
import id.ac.ui.cs.advprog.gamesappsstore.repository.app.AppDataRepository;
import id.ac.ui.cs.advprog.gamesappsstore.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VerificationService {
    @Autowired
    private AppDataRepository appDataRepository;
    @Autowired
    private UserRepository userRepository;

    public List<AppData> findAllVerifiedApps() {
        return appDataRepository.findByVerificationStatus(VerificationStatus.VERIFIED);
    }

    public List<AppData> findAllUnverifiedApps() {
        return appDataRepository.findByVerificationStatus(VerificationStatus.UNVERIFIED);
    }

    public VerificationDetailResponse getAppDetail(Long id) {
        AppData appData = getAppOrNotFound(id);
        return new VerificationDetailResponse(
                appData.getId(),
                appData.getName(),
                appData.getImageUrl(),
                appData.getDescription(),
                appData.getInstallerUrl(),
                appData.getVersion(),
                appData.getPrice(),
                appData.getVerificationStatus().toString(),
                appData.getVerificationAdminId(),
                appData.getVerificationDate()
        );
    }

    public void verify(Integer adminId, Long id) {
        User admin = userRepository.getById(adminId);
        AppData appData = getAppOrNotFound(id);
        AppDataVerification verification = new AppDataVerification(appData);
        verification.verify(admin);
        appDataRepository.save(appData);
    }

    public void reject(Integer adminId, Long id) {
        User admin = userRepository.getById(adminId);
        AppData appData = getAppOrNotFound(id);
        AppDataVerification verification = new AppDataVerification(appData);
        verification.reject(admin);
        appDataRepository.save(appData);
    }

    public void requestReverification(Integer userId, Long id) {
        AppData appData = getAppOrNotFound(id);
        if (!appData.getUserId().equals(userId)) {
            throw new UnauthorizedException("User is not the app's owner");
        }
        AppDataVerification verification = new AppDataVerification(appData);
        verification.requestReverification();
        appDataRepository.save(appData);
    }

    private AppData getAppOrNotFound(Long id) {
        var appData = appDataRepository.findById(id);
        if (appData.isEmpty()) {
            throw new AppDataDoesNotExistException();
        }
        return appData.get();
    }
}
