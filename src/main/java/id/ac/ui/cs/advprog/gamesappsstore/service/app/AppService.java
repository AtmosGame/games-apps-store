package id.ac.ui.cs.advprog.gamesappsstore.service.app;

import id.ac.ui.cs.advprog.gamesappsstore.dto.app.AppDetailResponse;

public interface AppService {
    AppDetailResponse getAppDetail(Long id);
}
