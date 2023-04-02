package id.ac.ui.cs.advprog.gamesappsstore.models.AppRegistration;

public class AppNameValidator extends AppDataValidatorChain{
    @Override
    public boolean validate(AppData appData){
        if (appData.getName() == null) {
            return false;
        }
        return checkNext(appData);
    }
}
