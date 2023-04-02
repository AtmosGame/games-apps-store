package id.ac.ui.cs.advprog.gamesappsstore.models.AppRegistration;

public class VersionValidator extends AppDataValidatorChain {
    @Override
    public boolean validate(AppData appData)  {
        if (!appData.getVersion().matches("\\d+\\.\\d+\\.\\d+")) {
            return false;
        }
        return checkNext(appData);
    }
}
