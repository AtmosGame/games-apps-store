package id.ac.ui.cs.advprog.gamesappsstore.core.app.validator;

import id.ac.ui.cs.advprog.gamesappsstore.models.app.AppData;
import id.ac.ui.cs.advprog.gamesappsstore.exceptions.CRUDAppData.EmptyFormException;
import id.ac.ui.cs.advprog.gamesappsstore.exceptions.CRUDAppData.PriceRangeException;
import org.springframework.stereotype.Component;

@Component
public class PriceValidator extends AppDataValidatorChain {
    @Override
    public boolean validate(AppData appData){
        if(appData.getPrice() == null){
            throw new EmptyFormException("Harga");
        }
        else if (appData.getPrice() < 0 || appData.getPrice() > 1000000) {
            throw new PriceRangeException();
        }
        return checkNext(appData);
    }
}
