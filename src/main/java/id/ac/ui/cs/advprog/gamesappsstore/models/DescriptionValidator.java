package id.ac.ui.cs.advprog.gamesappsstore.models;

public class DescriptionValidator extends AppDataValidatorChain {
    @Override
    public boolean validate(AppData appData){
        if (appData.getDescription().length() > 200) {
            return false;
        }
        return checkNext(appData);
    }
}
