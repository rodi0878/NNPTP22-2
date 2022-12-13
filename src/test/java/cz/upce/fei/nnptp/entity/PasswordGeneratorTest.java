package cz.upce.fei.nnptp.entity;

import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PasswordGeneratorTest {
    @Test
    void generatePasswordWithAllOptionsTest() {
        PasswordGenerator passwordGenerator = new PasswordGenerator();
        //long password to ensure it contains at least one character from each option
        String generatedPassword = passwordGenerator.generatePassword(100,
                new PasswordGeneratorConfiguration(true, true, true));
        assertEquals(100, generatedPassword.length());
        assertTrue(generatedPassword.chars().anyMatch(Character::isUpperCase));
        assertTrue((generatedPassword.chars().anyMatch(Character::isDigit)));
        assertTrue(Pattern.compile("[+?!<>*_#@%]").matcher(generatedPassword).find());
    }

    @Test
    void generateShortPasswordWithAllOptionsTest() {
        PasswordGenerator passwordGenerator = new PasswordGenerator();
        String shortPassword = passwordGenerator.generatePassword(4,
                new PasswordGeneratorConfiguration(true, true, true));
        assertTrue(shortPassword.chars().anyMatch(Character::isUpperCase));
        assertTrue((shortPassword.chars().anyMatch(Character::isDigit)));
        assertTrue(Pattern.compile("[+?!<>*_#@%]").matcher(shortPassword).find());
    }
}