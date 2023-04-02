package id.ac.ui.cs.advprog.gamesappsstore.models.AppRegistration;

import org.springframework.stereotype.Component;

@Component
public class AppDataValidator {

    private AppDataValidatorChain validatorChain;

    public AppDataValidator() {
        AppDataValidatorChain validatorChain1 = new DescriptionValidator();
        AppDataValidatorChain validatorChain2 = new PriceValidator();
        AppDataValidatorChain validatorChain3 = new VersionValidator();
        AppDataValidatorChain validatorChain4 = new AppNameValidator();
        AppDataValidatorChain validatorChain5 = new InstallerValidator();

        validatorChain1.linkWith(validatorChain2);
        validatorChain2.linkWith(validatorChain3);
        validatorChain3.linkWith(validatorChain4);
        validatorChain4.linkWith(validatorChain5);

        validatorChain = validatorChain1;
    }

    public boolean validate(AppData appData){
        return validatorChain.validate(appData);
    }
}
