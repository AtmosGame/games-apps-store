package id.ac.ui.cs.advprog.gamesappsstore.service.app;

import id.ac.ui.cs.advprog.gamesappsstore.models.app.AppData;
import id.ac.ui.cs.advprog.gamesappsstore.dto.app.AppDetailFullResponse;

public interface AppDetailService {
    AppDetailFullResponse getAppDetailbyId(Long id);
    AppDetailFullResponse createAppDetailResponse(AppData appData);
}
