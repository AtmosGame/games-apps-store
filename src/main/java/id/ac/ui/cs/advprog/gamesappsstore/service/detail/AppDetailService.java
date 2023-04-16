package id.ac.ui.cs.advprog.gamesappsstore.service.appdetail;

import id.ac.ui.cs.advprog.gamesappsstore.core.app.AppData;
import id.ac.ui.cs.advprog.gamesappsstore.dto.AppDetailResponse;

public interface AppDetailService {
    AppDetailResponse getAppDetailbyId(Long id);
    AppDetailResponse createAppDetailResponse(AppData appData);
}
