package id.ac.ui.cs.advprog.gamesappsstore.service.verification;

import id.ac.ui.cs.advprog.gamesappsstore.dto.verification.VerificationDetailResponse;
import id.ac.ui.cs.advprog.gamesappsstore.models.app.AppData;
import id.ac.ui.cs.advprog.gamesappsstore.models.auth.User;

import java.util.List;

public interface VerificationService {
    List<AppData> findAllVerifiedApps();
    List<AppData> findAllUnverifiedApps();
    VerificationDetailResponse getAppDetail(Long id);
    void verify(User admin, Long id);
    void reject(User admin, Long id);
    void requestReverification(User user, Long id);
}
