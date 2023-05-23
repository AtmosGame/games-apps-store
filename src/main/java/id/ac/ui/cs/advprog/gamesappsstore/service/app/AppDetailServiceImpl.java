package id.ac.ui.cs.advprog.gamesappsstore.service.app;

import id.ac.ui.cs.advprog.gamesappsstore.dto.app.AppDetailValidationRequest;
import id.ac.ui.cs.advprog.gamesappsstore.dto.app.AppDetailValidationResponse;
import id.ac.ui.cs.advprog.gamesappsstore.exceptions.crudapp.AppDataDoesNotExistException;
import id.ac.ui.cs.advprog.gamesappsstore.models.app.AppData;
import id.ac.ui.cs.advprog.gamesappsstore.dto.app.AppDetailFullResponse;
import id.ac.ui.cs.advprog.gamesappsstore.repository.app.AppDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppDetailServiceImpl implements AppDetailService {
    private final AppDataRepository appDataRepository;

    public AppDetailFullResponse getAppDetailbyId(Long id){
        Optional<AppData> appData = appDataRepository.findById(id);
        return appData.map(this::createAppDetailResponse).orElse(null);
    }

    public AppDetailValidationResponse validateApp(AppDetailValidationRequest request) {
        var appDataOptional = appDataRepository.findById(request.getId());
        if (appDataOptional.isEmpty()) {
            throw new AppDataDoesNotExistException();
        }
        AppData appData = appDataOptional.get();
        if (!appData.getName().equals(request.getName())) {
            return new AppDetailValidationResponse(false);
        }
        if (!appData.getPrice().equals(request.getPrice())) {
            return new AppDetailValidationResponse(false);
        }
        return new AppDetailValidationResponse(true);
    }

    public AppDetailFullResponse createAppDetailResponse(AppData appData){
        return AppDetailFullResponse.builder()
                .name(appData.getName())
                .imageUrl(appData.getImageUrl())
                .installerUrl(appData.getInstallerUrl())
                .description(appData.getDescription())
                .version(appData.getVersion())
                .price(appData.getPrice())
                .build();
    }
}
