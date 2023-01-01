package cz.upce.fei.nnptp.entity;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordGenerator {
    private static final int DEFAULT_PASSWORD_LENGTH = 5;
    private static final int MIN_PASS_LENGTH_TO_GUARANTEE_CHARACTERS = 4;

    private enum TypeOfCharacter {
        DIGITS("0123456789"),
        LOWER_CASE_CHARACTERS("abcdefghijklmnopqrstuvwxyz"),
        UPPER_CASE_CHARACTERS("ABCDEFGHIJKLMNOPQRSTUVWXYZ"),
        ALLOWED_SPECIAL_CHARACTERS("+?!<>*_#@%");

        private final String characters;

        TypeOfCharacter(String characters) {
            this.characters = characters;
        }
    }

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
            String mergedCharacters = TypeOfCharacter.ALLOWED_SPECIAL_CHARACTERS.characters;
            if (configuration.isIncludeCapitalLettersInPassword()) {
                mergedCharacters = mergedCharacters + TypeOfCharacter.LOWER_CASE_CHARACTERS.characters + TypeOfCharacter.UPPER_CASE_CHARACTERS.characters;
            } else {
                mergedCharacters = mergedCharacters + TypeOfCharacter.LOWER_CASE_CHARACTERS.characters;
            }
            if (configuration.isIncludeNumbersInPassword()) {
                mergedCharacters = mergedCharacters + TypeOfCharacter.DIGITS.characters;
            }

            outputPassword = RandomStringUtils.random(passwordLength, 0, mergedCharacters.length(), false, false, mergedCharacters.toCharArray());
        }

        HashMap<TypeOfCharacter, Integer> characterCounter = countCharacters(outputPassword);
        if (passwordLength < MIN_PASS_LENGTH_TO_GUARANTEE_CHARACTERS) {
            System.out.println("Warning! Password too short to guarantee inclusion of some characters from enabled options");
        } else {
            if (configuration.isIncludeNumbersInPassword() && characterCounter.get(TypeOfCharacter.DIGITS) == 0) {
                outputPassword = updatePassword(outputPassword, characterCounter, TypeOfCharacter.DIGITS);
            }
            if (configuration.isIncludeCapitalLettersInPassword() && characterCounter.get(TypeOfCharacter.UPPER_CASE_CHARACTERS) == 0) {
                outputPassword = updatePassword(outputPassword, characterCounter, TypeOfCharacter.UPPER_CASE_CHARACTERS);
            }
            if (configuration.isIncludeSpecialCharactersInPassword() && characterCounter.get(TypeOfCharacter.ALLOWED_SPECIAL_CHARACTERS) == 0) {
                outputPassword = updatePassword(outputPassword, characterCounter, TypeOfCharacter.ALLOWED_SPECIAL_CHARACTERS);
            }
            if (configuration.isIncludeSpecialCharactersInPassword() && characterCounter.get(TypeOfCharacter.LOWER_CASE_CHARACTERS) == 0) {
                outputPassword = updatePassword(outputPassword, characterCounter, TypeOfCharacter.LOWER_CASE_CHARACTERS);
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

    private HashMap<TypeOfCharacter, Integer> countCharacters(String input) {
        HashMap<TypeOfCharacter, Integer> characterCounter = new HashMap<>();
        characterCounter.put(TypeOfCharacter.DIGITS, getNumberOfOccurrencesMatchingRegEx(input, "[0-9]"));
        characterCounter.put(TypeOfCharacter.UPPER_CASE_CHARACTERS, getNumberOfOccurrencesMatchingRegEx(input, "[A-Z]"));
        characterCounter.put(TypeOfCharacter.LOWER_CASE_CHARACTERS, getNumberOfOccurrencesMatchingRegEx(input, "[a-z]"));
        characterCounter.put(TypeOfCharacter.ALLOWED_SPECIAL_CHARACTERS, getNumberOfOccurrencesMatchingRegEx(input, "[+?!<>*_#@%]"));
        return characterCounter;
    }

    private String updatePassword(String password, HashMap<TypeOfCharacter, Integer> numberOfOccurrences, TypeOfCharacter whatToInsert) {
        TypeOfCharacter whatToReplace = Collections.max(numberOfOccurrences.entrySet(), HashMap.Entry.comparingByValue()).getKey();

        numberOfOccurrences.put(whatToInsert, numberOfOccurrences.get(whatToInsert) + 1);
        numberOfOccurrences.put(whatToReplace, numberOfOccurrences.get(whatToReplace) - 1);

        switch (whatToReplace) {
            case DIGITS:
                return password.replaceFirst("[0-9]", "" + whatToInsert.characters.charAt((int) (Math.random() * whatToInsert.characters.length())));
            case UPPER_CASE_CHARACTERS:
                return password.replaceFirst("[A-Z]", "" + whatToInsert.characters.charAt((int) (Math.random() * whatToInsert.characters.length())));
            case LOWER_CASE_CHARACTERS:
                return password.replaceFirst("[a-z]", "" + whatToInsert.characters.charAt((int) (Math.random() * whatToInsert.characters.length())));
            case ALLOWED_SPECIAL_CHARACTERS:
                return password.replaceFirst("[+?!<>*_#@%]", "" + whatToInsert.characters.charAt((int) (Math.random() * whatToInsert.characters.length())));
            default:
                return password;
        }
    }
}
