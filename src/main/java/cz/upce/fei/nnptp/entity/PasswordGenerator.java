package cz.upce.fei.nnptp.entity;

import org.apache.commons.lang3.RandomStringUtils;

public class PasswordGenerator {
    private static final String lowerCaseCharacters = "abcdefghijklmnopqrstuvwxyz";
    private static final String upperCaseCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String digits = "0123456789";
    private static final String allowedSpecialCharacters = "+?!<>*_#@%";
    private static final int defaultPasswordLength = 5;

    public PasswordGenerator() {
    }

    public String generatePassword(int passwordLength, PasswordGeneratorConfiguration configuration) {
        String outputPassword;
        if (passwordLength <= 0){
            System.out.println("Invalid password length, using default length");
            passwordLength = defaultPasswordLength;
        }
        if (!configuration.isIncludeSpecialCharactersInPassword()) {
            outputPassword = RandomStringUtils.random(passwordLength, true, configuration.isIncludeNumbersInPassword());
            if (!configuration.isIncludeCapitalLettersInPassword()) {
                outputPassword = outputPassword.toLowerCase();
            }
        } else {
            String mergedCharacters = allowedSpecialCharacters;
            if (configuration.isIncludeCapitalLettersInPassword()) {
                mergedCharacters = mergedCharacters + upperCaseCharacters;
            } else {
                mergedCharacters = mergedCharacters + lowerCaseCharacters;
            }
            if (configuration.isIncludeNumbersInPassword()) {
                mergedCharacters = mergedCharacters + digits;
            }

            outputPassword = RandomStringUtils.random(passwordLength, 0, mergedCharacters.length(), false, false, mergedCharacters.toCharArray());
        }
        return outputPassword;
    }
}
