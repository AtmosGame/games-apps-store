package id.ac.ui.cs.advprog.gamesappsstore.repository.AppRegistration;

import id.ac.ui.cs.advprog.gamesappsstore.models.AppRegistration.AppData;

import java.util.ArrayList;
import java.util.List;

public interface AppDataRepository {
    void addAppData(AppData appData);

    List<AppData> findByVerificationIsNull();

    void save(AppData appData);

    void deleteById(Integer id);
}
