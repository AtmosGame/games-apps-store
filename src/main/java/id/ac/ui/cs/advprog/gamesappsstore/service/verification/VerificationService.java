package id.ac.ui.cs.advprog.gamesappsstore.service.verification;

import id.ac.ui.cs.advprog.gamesappsstore.core.app.AppData;
import id.ac.ui.cs.advprog.gamesappsstore.core.user.User;
import id.ac.ui.cs.advprog.gamesappsstore.core.user.UserRole;
import id.ac.ui.cs.advprog.gamesappsstore.core.verification.AppDataVerification;
import id.ac.ui.cs.advprog.gamesappsstore.exceptions.AppDataNotFoundException;
import id.ac.ui.cs.advprog.gamesappsstore.models.app.VerificationStatus;
import id.ac.ui.cs.advprog.gamesappsstore.repository.appregistration.AppDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VerificationService {
    private final AppDataRepository appDataRepository;

    public List<AppData> findAllVerifiedApps() {
        return appDataRepository.findByVerificationStatus(VerificationStatus.VERIFIED);
    }

    public List<AppData> findAllUnverifiedApps() {
        return appDataRepository.findByVerificationStatusIsNull();
    }

    public void verify(Integer adminId, Long id) {
        User admin = new User(adminId, UserRole.ADMINISTRATOR); // TODO: replace placeholders
        AppData appData = getAppOrNotFound(id);
        AppDataVerification verification = new AppDataVerification(appData, appDataRepository);
        verification.verify(admin);
    }

    public void reject(Integer adminId, Long id) {
        User admin = new User(adminId, UserRole.ADMINISTRATOR); // TODO: replace placeholders
        AppData appData = getAppOrNotFound(id);
        AppDataVerification verification = new AppDataVerification(appData, appDataRepository);
        verification.reject(admin);
    }

    public void requestReverification(Long id) {
        AppData appData = getAppOrNotFound(id);
        AppDataVerification verification = new AppDataVerification(appData, appDataRepository);
        verification.requestReverification();
    }

    private AppData getAppOrNotFound(Long id) {
        var appData = appDataRepository.findById(id);
        if (appData.isEmpty()) {
            throw new AppDataNotFoundException(id);
        }
        return appData.get();
    }
}
