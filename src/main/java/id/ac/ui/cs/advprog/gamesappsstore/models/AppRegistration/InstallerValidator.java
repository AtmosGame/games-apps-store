package id.ac.ui.cs.advprog.gamesappsstore.models.AppRegistration;

public class InstallerValidator extends  AppDataValidatorChain{
    @Override
    public boolean validate(AppData appData){
        if (appData.getInstallerFile() == null) {
            return false;
        }
        return checkNext(appData);
    }
}
