package id.ac.ui.cs.advprog.gamesappsstore.core.app.validator;

import id.ac.ui.cs.advprog.gamesappsstore.models.app.AppData;

import java.util.*;

public class AppInstallerValidator {
    private AppDataValidatorChain validatorChain;
    public AppInstallerValidator() {
        AppDataValidatorChain validatorChain1 = new VersionValidator();
        AppDataValidatorChain validatorChain2 = new InstallerValidator();

        validatorChain1.linkWith(validatorChain2);

        validatorChain = validatorChain1;
    }

    public boolean isUpdatedVersion(String currentVersion, String beforeVersion){
        ArrayList<Integer> versionNumber = new ArrayList<Integer>();
        ArrayList<Integer> beforeVersionNumber = new ArrayList<Integer>();

        int currentNumber = 0;
        for(int i = 0; i < currentVersion.length(); i++){
            if(currentVersion.charAt(i) == '.'){
                currentNumber = 0;
                versionNumber.add(currentNumber);
            }
            else{
                char currentChar = currentVersion.charAt(i);
                currentNumber *= 10;
                currentNumber += currentChar - '0';
            }
        }

        currentNumber = 0;
        for(int i = 0; i < beforeVersion.length(); i++){
            if(beforeVersion.charAt(i) == '.'){
                currentNumber = 0;
                beforeVersionNumber.add(currentNumber);
            }
            else{
                char currentChar = beforeVersion.charAt(i);
                currentNumber *= 10;
                currentNumber += currentChar - '0';
            }
        }

        Boolean isValid = true;
        for(int i = 0; i < versionNumber.size(); i++){
            if(versionNumber.get(i) == beforeVersionNumber.get(i)){
                continue;
            }
            else if(versionNumber.get(i) < beforeVersionNumber.get(i)){
                isValid = false;
            }
            else{
                break;
            }
        }
        return isValid;
    }

    public boolean validate(AppData appData, String beforeVersion){
        return validatorChain.validate(appData) && isUpdatedVersion(appData.getVersion(), beforeVersion);
    }
}
