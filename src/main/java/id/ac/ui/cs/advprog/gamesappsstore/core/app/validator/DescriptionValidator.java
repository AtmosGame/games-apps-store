package id.ac.ui.cs.advprog.gamesappsstore.core.app.validator;

import id.ac.ui.cs.advprog.gamesappsstore.models.app.AppData;
import org.springframework.stereotype.Component;
@Component
public class DescriptionValidator extends AppDataValidatorChain {
    @Override
    public boolean validate(AppData appData){
        if (appData.getDescription().length() > 200) {
            return false;
        }
        return checkNext(appData);
    }
}
