package id.ac.ui.cs.advprog.gamesappsstore.service.app;

import id.ac.ui.cs.advprog.gamesappsstore.dto.download.AppDownloadResponse;

public interface AppDownloadService {
    AppDownloadResponse getDownloadUrl(String username, Long id, String jwtToken);
}
