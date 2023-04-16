package id.ac.ui.cs.advprog.gamesappsstore.service.apps;

import id.ac.ui.cs.advprog.gamesappsstore.core.app.AppData;
import id.ac.ui.cs.advprog.gamesappsstore.dto.apps.AppDetailResponse;
import id.ac.ui.cs.advprog.gamesappsstore.exceptions.AppDataNotFoundException;
import id.ac.ui.cs.advprog.gamesappsstore.repository.appregistration.AppDataRepository;
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
                appData.getPrice()
        );
    }

    private AppData getAppOrNotFound(Long id) {
        var appData = appDataRepository.findById(id);
        if (appData.isEmpty()) {
            throw new AppDataNotFoundException(id);
        }
        return appData.get();
    }
}
