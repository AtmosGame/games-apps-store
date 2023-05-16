package id.ac.ui.cs.advprog.gamesappsstore.core.app.validator;

import id.ac.ui.cs.advprog.gamesappsstore.models.app.AppData;
import id.ac.ui.cs.advprog.gamesappsstore.exceptions.crudapp.GreaterVersionException;

import java.util.*;

public class AppInstallerValidator {
    private AppDataValidatorChain validatorChain;
    public AppInstallerValidator() {
        AppDataValidatorChain validatorChain1 = new VersionValidator();
        AppDataValidatorChain validatorChain2 = new InstallerValidator();

        validatorChain1.linkWith(validatorChain2);

        validatorChain = validatorChain1;
    }

    public void isUpdatedVersion(String currentVersion, String beforeVersion){
        ArrayList<Integer> versionNumber = stringToList(currentVersion);
        ArrayList<Integer> beforeVersionNumber = stringToList(beforeVersion);

        boolean isValid = true;
        for(int i = 0; i < versionNumber.size(); i++){
            if(!versionNumber.get(i).equals(beforeVersionNumber.get(i))){
                if(versionNumber.get(i) < beforeVersionNumber.get(i)){
                    isValid = false;
                }
                else{
                    break;
                }
            }
        }
        if(!isValid){
            throw new GreaterVersionException();
        }
    }

    private ArrayList<Integer> stringToList(String version) {
        int currentNumber = 0;
        ArrayList<Integer> versionNumber = new ArrayList<>();
        for(int i = 0; i < version.length(); i++){
            if(version.charAt(i) == '.'){
                versionNumber.add(currentNumber);
                currentNumber = 0;
            }
            else{
                char currentChar = version.charAt(i);
                currentNumber *= 10;
                currentNumber += currentChar - '0';
            }
        }
        versionNumber.add(currentNumber);
        return versionNumber;
    }

    public void validate(AppData appData, String beforeVersion){
        if (validatorChain.validate(appData)) {
            isUpdatedVersion(appData.getVersion(), beforeVersion);
        }
    }
}
