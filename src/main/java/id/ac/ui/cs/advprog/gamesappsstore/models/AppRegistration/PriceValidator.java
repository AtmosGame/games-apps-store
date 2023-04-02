package id.ac.ui.cs.advprog.gamesappsstore.models.AppRegistration;

public class PriceValidator extends AppDataValidatorChain {
    @Override
    public boolean validate(AppData appData){
        if (appData.getPrice() < 0 || appData.getPrice() > 1000000 || appData.getPrice() == null) {
            return false;
        }
        return checkNext(appData);
    }
}
