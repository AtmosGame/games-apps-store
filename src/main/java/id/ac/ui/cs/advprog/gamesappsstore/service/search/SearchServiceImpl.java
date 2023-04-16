package id.ac.ui.cs.advprog.gamesappsstore.service.search;

import id.ac.ui.cs.advprog.gamesappsstore.core.app.AppData;
import id.ac.ui.cs.advprog.gamesappsstore.repository.appregistration.AppDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService{
    private final AppDataRepository appDataRepository;

    public List<AppData> searchAppsByKeyword(String keyword){
        return appDataRepository.searchAppsByKeyword(keyword);
    }
}
