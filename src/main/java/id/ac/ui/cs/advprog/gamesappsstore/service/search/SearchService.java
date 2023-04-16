package id.ac.ui.cs.advprog.gamesappsstore.service.search;

import id.ac.ui.cs.advprog.gamesappsstore.core.app.AppData;

import java.util.List;

public interface SearchService {
    List<AppData> searchAppsByKeyword(String keyword);
}
