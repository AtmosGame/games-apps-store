package id.ac.ui.cs.advprog.gamesappsstore.service.app;

import id.ac.ui.cs.advprog.gamesappsstore.models.app.AppData;

import java.util.List;

public interface SearchService {
    List<AppData> searchAppsByKeyword(String keyword);
}
