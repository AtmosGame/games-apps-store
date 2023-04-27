package id.ac.ui.cs.advprog.gamesappsstore.service.app;

import id.ac.ui.cs.advprog.gamesappsstore.models.app.AppData;
import id.ac.ui.cs.advprog.gamesappsstore.dto.app.AppDetailFullResponse;
import id.ac.ui.cs.advprog.gamesappsstore.repository.app.AppDataRepository;
import id.ac.ui.cs.advprog.gamesappsstore.service.app.AppDetailService;
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
