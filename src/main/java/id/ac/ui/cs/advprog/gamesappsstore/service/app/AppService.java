package id.ac.ui.cs.advprog.gamesappsstore.service.app;

import id.ac.ui.cs.advprog.gamesappsstore.exceptions.crudapp.AppDataDoesNotExistException;
import id.ac.ui.cs.advprog.gamesappsstore.models.app.AppData;
import id.ac.ui.cs.advprog.gamesappsstore.dto.app.AppDetailResponse;
import id.ac.ui.cs.advprog.gamesappsstore.repository.app.AppDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppService {
    private final AppDataRepository appDataRepository;

    public AppDetailResponse getAppDetail(Long id) {
        AppData appData = getAppOrNotFound(id);
        return new AppDetailResponse(
                appData.getId(),
                appData.getName(),
                appData.getImageUrl(),
                appData.getDescription(),
                appData.getVersion(),
                appData.getPrice(),
                appData.getVerificationStatus()
        );
    }

    private AppData getAppOrNotFound(Long id) {
        var appData = appDataRepository.findById(id);
        if (appData.isEmpty()) {
            throw new AppDataDoesNotExistException();
        }
        return appData.get();
    }
}
