package cz.upce.fei.nnptp.zz.entity;

import org.apache.commons.lang3.RandomStringUtils;

public class PasswordGenerator {

    private static final String lowerCaseCharacters = "abcdefghijklmnopqrstuvwxyz";
    private static final String upperCaseCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String digits = "0123456789";
    private static final String allowedSpecialCharacters = "+?!<>*_#@%";

    public PasswordGenerator() {
    }

    public String generatePassword(int passwordLength, boolean canIncludeCapitalLetters, boolean canIncludeNumbers, boolean canIncludeSpecialCharacters) {
        String outputPassword;
        if (!canIncludeSpecialCharacters) {
            outputPassword = RandomStringUtils.random(passwordLength, true, canIncludeNumbers);
            if (!canIncludeCapitalLetters) {
                outputPassword = outputPassword.toLowerCase();
            }
        } else {
            String mergedCharacters = allowedSpecialCharacters;
            if (canIncludeCapitalLetters) {
                mergedCharacters = mergedCharacters + upperCaseCharacters;
            } else {
                mergedCharacters = mergedCharacters + lowerCaseCharacters;
            }
            if (canIncludeNumbers) {
                mergedCharacters = mergedCharacters + digits;
            }

            outputPassword = RandomStringUtils.random(passwordLength, 0, mergedCharacters.length(), false, false, mergedCharacters.toCharArray());
        }
        return outputPassword;
    }
}
