package id.ac.ui.cs.advprog.gamesappsstore.core.app.validator;

import id.ac.ui.cs.advprog.gamesappsstore.models.app.AppData;
import org.springframework.stereotype.Component;

@Component
public class PriceValidator extends AppDataValidatorChain {
    @Override
    public boolean validate(AppData appData){
        if (appData.getPrice() < 0 || appData.getPrice() > 1000000 || appData.getPrice() == null) {
            return false;
        }
        return checkNext(appData);
    }
}
