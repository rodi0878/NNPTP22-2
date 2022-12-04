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

        HashMap<String, Integer> typesAndCountsOfCharactersInPassword = createOrUpdateHashMap(new HashMap<String, Integer>(), outputPassword);
        if (passwordLength < MIN_PASS_LENGTH_TO_GUARANTEE_CHARACTERS) {
            System.out.println("Warning! Password too short to guarantee inclusion of some characters from enabled options");
        }else{
            if (configuration.isIncludeNumbersInPassword() && typesAndCountsOfCharactersInPassword.get("NUMBERS") == 0) {
                outputPassword = updatePassword(outputPassword, typesAndCountsOfCharactersInPassword, "NUMBERS");
                typesAndCountsOfCharactersInPassword = createOrUpdateHashMap(typesAndCountsOfCharactersInPassword, outputPassword);
            }
            if (configuration.isIncludeCapitalLettersInPassword() && typesAndCountsOfCharactersInPassword.get("UPPERCASE") == 0) {
                outputPassword = updatePassword(outputPassword, typesAndCountsOfCharactersInPassword, "UPPERCASE");
                typesAndCountsOfCharactersInPassword = createOrUpdateHashMap(typesAndCountsOfCharactersInPassword, outputPassword);
            }
            if (configuration.isIncludeSpecialCharactersInPassword() && typesAndCountsOfCharactersInPassword.get("SPECIAL_CHARACTERS") == 0) {
                outputPassword = updatePassword(outputPassword, typesAndCountsOfCharactersInPassword, "SPECIAL_CHARACTERS");
                typesAndCountsOfCharactersInPassword = createOrUpdateHashMap(typesAndCountsOfCharactersInPassword, outputPassword);
            }
            if (configuration.isIncludeSpecialCharactersInPassword() && typesAndCountsOfCharactersInPassword.get("LOWERCASE") == 0) {
                outputPassword = updatePassword(outputPassword, typesAndCountsOfCharactersInPassword, "LOWERCASE");
                typesAndCountsOfCharactersInPassword = createOrUpdateHashMap(typesAndCountsOfCharactersInPassword, outputPassword);
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
    private HashMap<String, Integer> createOrUpdateHashMap(HashMap<String, Integer> typesAndCountsOfCharactersInPassword, String inputPassword) {
        typesAndCountsOfCharactersInPassword.clear();
        typesAndCountsOfCharactersInPassword.put("NUMBERS", getNumberOfOccurrencesMatchingRegEx(inputPassword, "[0-9]"));
        typesAndCountsOfCharactersInPassword.put("UPPERCASE", getNumberOfOccurrencesMatchingRegEx(inputPassword, "[A-Z]"));
        typesAndCountsOfCharactersInPassword.put("LOWERCASE", getNumberOfOccurrencesMatchingRegEx(inputPassword, "[a-z]"));
        typesAndCountsOfCharactersInPassword.put("SPECIAL_CHARACTERS", getNumberOfOccurrencesMatchingRegEx(inputPassword, "[+?!<>*_#@%]"));
        return typesAndCountsOfCharactersInPassword;
    }
    private String updatePassword(String inputPassword, HashMap<String, Integer> mapWithNumberOfOccurrences, String keyOfWhatToInsert) {
        String sourceOfCharactersToUseInReplacement = "";
        switch (keyOfWhatToInsert) {
            case "NUMBERS":
                sourceOfCharactersToUseInReplacement = DIGITS;
                break;
            case "UPPERCASE":
                sourceOfCharactersToUseInReplacement = UPPER_CASE_CHARACTERS;
                break;
            case "LOWERCASE":
                sourceOfCharactersToUseInReplacement = LOWER_CASE_CHARACTERS;
                break;
            case "SPECIAL_CHARACTERS":
                sourceOfCharactersToUseInReplacement = ALLOWED_SPECIAL_CHARACTERS;
                break;
        }
        String keyOfObjectWithHighestValue = Collections.max(mapWithNumberOfOccurrences.entrySet(), HashMap.Entry.comparingByValue()).getKey();
        switch (keyOfObjectWithHighestValue) {
            case "NUMBERS":
                inputPassword = inputPassword.replaceFirst("[0-9]",
                        Character.toString(sourceOfCharactersToUseInReplacement.charAt((int) (Math.random() * sourceOfCharactersToUseInReplacement.length()))));
                return inputPassword;
            case "UPPERCASE":
                inputPassword = inputPassword.replaceFirst("[A-Z]",
                        Character.toString(sourceOfCharactersToUseInReplacement.charAt((int) (Math.random() * sourceOfCharactersToUseInReplacement.length()))));
                return inputPassword;
            case "LOWERCASE":
                inputPassword = inputPassword.replaceFirst("[a-z]",
                        Character.toString(sourceOfCharactersToUseInReplacement.charAt((int) (Math.random() * sourceOfCharactersToUseInReplacement.length()))));
                return inputPassword;
            case "SPECIAL_CHARACTERS":
                inputPassword = inputPassword.replaceFirst("[+?!<>*_#@%]",
                        Character.toString(sourceOfCharactersToUseInReplacement.charAt((int) (Math.random() * sourceOfCharactersToUseInReplacement.length()))));
                return inputPassword;
            default:
                return inputPassword;
        }
    }
}
