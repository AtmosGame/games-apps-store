package id.ac.ui.cs.advprog.gamesappsstore.core.app.validator;

import id.ac.ui.cs.advprog.gamesappsstore.models.app.AppData;
import org.springframework.stereotype.Component;

@Component
public class VersionValidator extends AppDataValidatorChain {
    @Override
    public boolean validate(AppData appData)  {
        if (!appData.getVersion().matches("\\d+\\.\\d+\\.\\d+")) {
            return false;
        }
        return checkNext(appData);
    }
}
