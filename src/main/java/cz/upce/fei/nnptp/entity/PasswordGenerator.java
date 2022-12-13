package cz.upce.fei.nnptp.entity;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordGenerator {
    private static final String LOWER_CASE_CHARACTERS = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPER_CASE_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final String ALLOWED_SPECIAL_CHARACTERS = "+?!<>*_#@%";
    private static final int DEFAULT_PASSWORD_LENGTH = 5;
    private static final int MIN_PASS_LENGTH_TO_GUARANTEE_CHARACTERS = 4;

    public PasswordGenerator() {
    }

    public String generatePassword(int passwordLength, PasswordGeneratorConfiguration configuration) {
        String outputPassword;
        if (passwordLength <= 0) {
            System.out.println("Invalid password length, using default length");
            passwordLength = DEFAULT_PASSWORD_LENGTH;
        }

        if (!configuration.isIncludeSpecialCharactersInPassword()) {
            outputPassword = RandomStringUtils.random(passwordLength, true, configuration.isIncludeNumbersInPassword());
            if (!configuration.isIncludeCapitalLettersInPassword()) {
                outputPassword = outputPassword.toLowerCase();
            }
        } else {
            String mergedCharacters = ALLOWED_SPECIAL_CHARACTERS;
            if (configuration.isIncludeCapitalLettersInPassword()) {
                mergedCharacters = mergedCharacters + LOWER_CASE_CHARACTERS + UPPER_CASE_CHARACTERS;
            } else {
                mergedCharacters = mergedCharacters + LOWER_CASE_CHARACTERS;
            }
            if (configuration.isIncludeNumbersInPassword()) {
                mergedCharacters = mergedCharacters + DIGITS;
            }

            outputPassword = RandomStringUtils.random(passwordLength, 0, mergedCharacters.length(), false, false, mergedCharacters.toCharArray());
        }

        HashMap<String, Integer> characterCounter = countCharacters(outputPassword);
        if (passwordLength < MIN_PASS_LENGTH_TO_GUARANTEE_CHARACTERS) {
            System.out.println("Warning! Password too short to guarantee inclusion of some characters from enabled options");
        } else {
            if (configuration.isIncludeNumbersInPassword() && characterCounter.get(DIGITS) == 0) {
                outputPassword = updatePassword(outputPassword, characterCounter, DIGITS);
            }
            if (configuration.isIncludeCapitalLettersInPassword() && characterCounter.get(UPPER_CASE_CHARACTERS) == 0) {
                outputPassword = updatePassword(outputPassword, characterCounter, UPPER_CASE_CHARACTERS);
            }
            if (configuration.isIncludeSpecialCharactersInPassword() && characterCounter.get(ALLOWED_SPECIAL_CHARACTERS) == 0) {
                outputPassword = updatePassword(outputPassword, characterCounter, ALLOWED_SPECIAL_CHARACTERS);
            }
            if (configuration.isIncludeSpecialCharactersInPassword() && characterCounter.get(LOWER_CASE_CHARACTERS) == 0) {
                outputPassword = updatePassword(outputPassword, characterCounter, LOWER_CASE_CHARACTERS);
            }
        }

        return outputPassword;
    }

    private int getNumberOfOccurrencesMatchingRegEx(String input, String regex) {
        Matcher matcher = Pattern.compile(regex).matcher(input);
        int count = 0;
        while (matcher.find()) {
            count += matcher.group().length();
        }
        return count;
    }

    private HashMap<String, Integer> countCharacters(String input) {
        HashMap<String, Integer> characterCounter = new HashMap<>();
        characterCounter.put(DIGITS, getNumberOfOccurrencesMatchingRegEx(input, "[0-9]"));
        characterCounter.put(UPPER_CASE_CHARACTERS, getNumberOfOccurrencesMatchingRegEx(input, "[A-Z]"));
        characterCounter.put(LOWER_CASE_CHARACTERS, getNumberOfOccurrencesMatchingRegEx(input, "[a-z]"));
        characterCounter.put(ALLOWED_SPECIAL_CHARACTERS, getNumberOfOccurrencesMatchingRegEx(input, "[+?!<>*_#@%]"));
        return characterCounter;
    }

    private String updatePassword(String password, HashMap<String, Integer> numberOfOccurrences, String whatToInsert) {
        String whatToReplace = Collections.max(numberOfOccurrences.entrySet(), HashMap.Entry.comparingByValue()).getKey();

        numberOfOccurrences.put(whatToInsert, numberOfOccurrences.get(whatToInsert) + 1);
        numberOfOccurrences.put(whatToReplace, numberOfOccurrences.get(whatToReplace) - 1);

        switch (whatToReplace) {
            case DIGITS:
                return password.replaceFirst("[0-9]", "" + whatToInsert.charAt((int) (Math.random() * whatToInsert.length())));
            case UPPER_CASE_CHARACTERS:
                return password.replaceFirst("[A-Z]", "" + whatToInsert.charAt((int) (Math.random() * whatToInsert.length())));
            case LOWER_CASE_CHARACTERS:
                return password.replaceFirst("[a-z]", "" + whatToInsert.charAt((int) (Math.random() * whatToInsert.length())));
            case ALLOWED_SPECIAL_CHARACTERS:
                return password.replaceFirst("[+?!<>*_#@%]", "" + whatToInsert.charAt((int) (Math.random() * whatToInsert.length())));
            default:
                return password;
        }
    }
}
