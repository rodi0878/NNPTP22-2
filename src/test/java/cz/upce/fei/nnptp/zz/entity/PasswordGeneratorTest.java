package cz.upce.fei.nnptp.zz.entity;

import org.junit.jupiter.api.Test;

import javax.xml.stream.events.Characters;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class PasswordGeneratorTest {
    @Test
    void generatePasswordWithAllOptionsTest() {
        PasswordGenerator passwordGenerator = new PasswordGenerator();
        //long password to ensure it contains at least one character from each option
        String generatedPassword = passwordGenerator.generatePassword(100, true, true, true);
        assertTrue(generatedPassword.length() == 100);
        assertTrue(generatedPassword.chars().anyMatch(Character::isUpperCase));
        assertTrue((generatedPassword.chars().anyMatch(Character::isDigit)));
        assertTrue(Pattern.compile("[+?!<>*_#@%]").matcher(generatedPassword).find());
    }
}