package id.ac.ui.cs.advprog.gamesappsstore.service.app;

import id.ac.ui.cs.advprog.gamesappsstore.models.app.AppData;
import id.ac.ui.cs.advprog.gamesappsstore.models.app.enums.VerificationStatus;
import id.ac.ui.cs.advprog.gamesappsstore.repository.app.AppDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {
    private final AppDataRepository appDataRepository;

    public List<AppData> searchAppsByKeyword(String keyword) {
        List<AppData> appList = appDataRepository.searchAppsByKeyword(keyword);
        List<AppData> filteredList = new ArrayList<>();

        for (AppData app : appList) {
            if (app.getVerificationStatus() == VerificationStatus.VERIFIED) {
                filteredList.add(app);
            }
        }

        return filteredList;
    }

}
