package id.ac.ui.cs.advprog.gamesappsstore.core.app.validator;
import id.ac.ui.cs.advprog.gamesappsstore.models.app.AppData;
import id.ac.ui.cs.advprog.gamesappsstore.exceptions.crudapp.EmptyFormException;
import org.springframework.stereotype.Component;

@Component
public class AppNameValidator extends AppDataValidatorChain{
    @Override
    public boolean validate(AppData appData){
        if (appData.getName() == null || appData.getName().equals("")) {
            throw new EmptyFormException("Nama");
        }
        return checkNext(appData);
    }
}
