package id.ac.ui.cs.advprog.gamesappsstore.core.app.validator;
import id.ac.ui.cs.advprog.gamesappsstore.models.app.AppData;
import id.ac.ui.cs.advprog.gamesappsstore.exceptions.CRUDAppData.EmptyFormException;
import id.ac.ui.cs.advprog.gamesappsstore.exceptions.CRUDAppData.LongDescriptionException;
import org.springframework.stereotype.Component;
@Component
public class DescriptionValidator extends AppDataValidatorChain {
    @Override
    public boolean validate(AppData appData){
        if(appData.getDescription() == null){
            throw new EmptyFormException("Deskripsi");
        }

        else if (appData.getDescription().length() > 200) {
            throw new LongDescriptionException();
        }
        return checkNext(appData);
    }
}
