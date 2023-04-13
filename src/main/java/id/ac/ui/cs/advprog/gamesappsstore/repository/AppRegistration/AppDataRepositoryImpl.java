package id.ac.ui.cs.advprog.gamesappsstore.repository.AppRegistration;

import id.ac.ui.cs.advprog.gamesappsstore.models.AppRegistration.AppData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppDataRepositoryImpl implements  AppDataRepository{
    private final Map<String, AppData> appDatas = new HashMap<>();

    @Override
    public void addAppData(AppData appData) {
        String id = getId();
        appDatas.put(id, appData);
    }

    public String getId(){
        String id = Integer.toString(appDatas.size());
        return id;
    }

    public List<AppData> findByVerificationIsNull() {
        return new ArrayList<>(appDatas.values());
    }

    public void save(AppData appData) {
        appDatas.put(appData.getId(), appData);
    }

    public void deleteById(Integer id) {
        appDatas.remove(id);
    }
}
