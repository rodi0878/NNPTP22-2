package cz.upce.fei.nnptp.entity;

import cz.upce.fei.nnptp.exception.ValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PasswordParameterTest {

    @Test
    void constructorShouldThrowValidationExceptionWhenNullPassed() {
        String nullString = null;

        Exception exception = assertThrows(ValidationException.class, () -> {
            new Parameter.PasswordParameter(nullString);
        });

        assertEquals("Value for PasswordParameter is not valid.", exception.getMessage());
    }

    @Test
    void constructorShouldThrowValidationExceptionWhenEmptyStringPassed() {
        String emptyString = "";

        Exception exception = assertThrows(ValidationException.class, () -> {
            new Parameter.PasswordParameter(emptyString);
        });

        assertEquals("Value for PasswordParameter is not valid.", exception.getMessage());
    }

    @Test
    void setValueShouldThrowValidationExceptionWhenNullPassed() {
        String nullString = null;
        Parameter.PasswordParameter validValue = new Parameter.PasswordParameter("ValidValue");

        Exception exception = assertThrows(ValidationException.class, () -> {
            validValue.setPassword(nullString);
        });

        assertEquals("New value for PasswordParameter is not valid.", exception.getMessage());
    }

    @Test
    void setValueShouldThrowValidationExceptionWhenEmptyStringPassed() {
        String emptyString = "";
        Parameter.PasswordParameter validValue = new Parameter.PasswordParameter("ValidValue");

        Exception exception = assertThrows(ValidationException.class, () -> {
            validValue.setPassword(emptyString);
        });

        assertEquals("New value for PasswordParameter is not valid.", exception.getMessage());
    }
}