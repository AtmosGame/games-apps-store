package id.ac.ui.cs.advprog.gamesappsstore.service.appdetail;

import id.ac.ui.cs.advprog.gamesappsstore.core.app.AppData;
import id.ac.ui.cs.advprog.gamesappsstore.dto.AppDetailResponse;
import id.ac.ui.cs.advprog.gamesappsstore.repository.appregistration.AppDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppDetailServiceImpl implements AppDetailService{
    private final AppDataRepository appDataRepository;

    public AppDetailResponse getAppDetailbyId(Long id){
        Optional<AppData> appData = appDataRepository.findById(id);
        return appData.map(this::createAppDetailResponse).orElse(null);
    }

    public AppDetailResponse createAppDetailResponse(AppData appData){
        return AppDetailResponse.builder()
                .name(appData.getName())
                .imageUrl(appData.getImageUrl())
                .installerUrl(appData.getInstallerUrl())
                .description(appData.getDescription())
                .version(appData.getVersion())
                .price(appData.getPrice())
                .build();
    }
}
