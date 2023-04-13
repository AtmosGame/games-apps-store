package id.ac.ui.cs.advprog.gamesappsstore.core.app.validator;

import id.ac.ui.cs.advprog.gamesappsstore.core.app.AppData;
import org.springframework.stereotype.Component;

@Component
public class InstallerValidator extends  AppDataValidatorChain{
    @Override
    public boolean validate(AppData appData){
        if (appData.getInstallerUrl() == null) {
            return false;
        }
        return checkNext(appData);
    }
}
