package id.ac.ui.cs.advprog.gamesappsstore.repository;

import id.ac.ui.cs.advprog.gamesappsstore.models.AppData;

import java.util.HashMap;
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
}
