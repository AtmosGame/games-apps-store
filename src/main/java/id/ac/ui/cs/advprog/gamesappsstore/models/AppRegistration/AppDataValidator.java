package id.ac.ui.cs.advprog.gamesappsstore.models.AppRegistration;

import org.springframework.stereotype.Component;

@Component
public class AppDataValidator {

    private AppDataValidatorChain validatorChain;

    public AppDataValidator() {
        AppDataValidatorChain validatorChain1 = new DescriptionValidator();
        AppDataValidatorChain validatorChain2 = new PriceValidator();
        AppDataValidatorChain validatorChain3 = new VersionValidator();

        validatorChain1.linkWith(validatorChain2);
        validatorChain2.linkWith(validatorChain3);

        validatorChain = validatorChain1;
    }

    public boolean validate(AppData appData){
        return validatorChain.validate(appData);
    }
}
