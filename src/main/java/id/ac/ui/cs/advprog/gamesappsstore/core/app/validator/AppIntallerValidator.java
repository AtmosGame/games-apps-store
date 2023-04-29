package id.ac.ui.cs.advprog.gamesappsstore.core.app.validator;

import id.ac.ui.cs.advprog.gamesappsstore.models.app.AppData;
import id.ac.ui.cs.advprog.gamesappsstore.exceptions.CRUDAppData.GreaterVersionException;

import java.util.*;

public class AppIntallerValidator {
    private AppDataValidatorChain validatorChain;
    public AppIntallerValidator() {
        AppDataValidatorChain validatorChain1 = new VersionValidator();
        AppDataValidatorChain validatorChain2 = new InstallerValidator();

        validatorChain1.linkWith(validatorChain2);

        validatorChain = validatorChain1;
    }

    public boolean isUpdatedVersion(String currentVersion, String beforeVersion){
        ArrayList<Integer> versionNumber = new ArrayList<Integer>();
        ArrayList<Integer> beforeVersionNumber = new ArrayList<Integer>();
        System.out.println(currentVersion + " test " + beforeVersion);
        int currentNumber = 0;
        for(int i = 0; i < currentVersion.length(); i++){
            if(currentVersion.charAt(i) == '.'){
                versionNumber.add(currentNumber);
                currentNumber = 0;
            }
            else{
                char currentChar = currentVersion.charAt(i);
                currentNumber *= 10;
                currentNumber += currentChar - '0';
            }
        }
        versionNumber.add(currentNumber);


        currentNumber = 0;
        for(int i = 0; i < beforeVersion.length(); i++){
            if(beforeVersion.charAt(i) == '.'){
                beforeVersionNumber.add(currentNumber);
                currentNumber = 0;
            }
            else{
                char currentChar = beforeVersion.charAt(i);
                currentNumber *= 10;
                currentNumber += currentChar - '0';
            }
        }
        beforeVersionNumber.add(currentNumber);

        Boolean isValid = true;
        for(int i = 0; i < versionNumber.size(); i++){
            System.out.println(versionNumber.get(i) + " test " + beforeVersionNumber.get(i));
            if(versionNumber.get(i).equals(beforeVersionNumber.get(i))){
                continue;
            }
            else if(versionNumber.get(i) < beforeVersionNumber.get(i)){
                isValid = false;
            }
            else{
                break;
            }
        }
        System.out.println("isvalid " + isValid);
        if(!isValid){

            throw new GreaterVersionException();
        }
        else{
            return isValid;
        }
    }

    public boolean validate(AppData appData, String beforeVersion){
        return validatorChain.validate(appData) && isUpdatedVersion(appData.getVersion(), beforeVersion);
    }
}
