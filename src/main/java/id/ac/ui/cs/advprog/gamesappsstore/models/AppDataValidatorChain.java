package id.ac.ui.cs.advprog.gamesappsstore.models;

import org.springframework.stereotype.Component;

@Component
public abstract class AppDataValidatorChain {
    private AppDataValidatorChain next;

    public AppDataValidatorChain linkWith(AppDataValidatorChain next) {
        this.next = next;
        return next;
    }

    public abstract boolean validate(AppData appData);

    protected boolean checkNext(AppData appData){
        if (next == null) {
            return true;
        }
        return next.validate(appData);
    }
}
