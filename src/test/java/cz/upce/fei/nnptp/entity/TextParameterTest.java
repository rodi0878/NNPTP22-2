package cz.upce.fei.nnptp.entity;

import cz.upce.fei.nnptp.exception.ValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TextParameterTest {

    @Test
    void constructorShouldThrowValidationExceptionWhenNullPassed() {
        String nullString = null;

        Exception exception = assertThrows(ValidationException.class, () -> {
            new Parameter.TextParameter(nullString);
        });

        assertEquals("Value for TextParameter is not valid.", exception.getMessage());
    }

    @Test
    void constructorShouldThrowValidationExceptionWhenEmptyStringPassed() {
        String emptyString = "";

        Exception exception = assertThrows(ValidationException.class, () -> {
            new Parameter.TextParameter(emptyString);
        });

        assertEquals("Value for TextParameter is not valid.", exception.getMessage());
    }

    @Test
    void setValueShouldThrowValidationExceptionWhenNullPassed() {
        String nullString = null;
        Parameter.TextParameter validValue = new Parameter.TextParameter("ValidValue");

        Exception exception = assertThrows(ValidationException.class, () -> {
            validValue.setValue(nullString);
        });

        assertEquals("New value for TextParameter is not valid.", exception.getMessage());
    }

    @Test
    void setValueShouldThrowValidationExceptionWhenEmptyStringPassed() {
        String emptyString = "";
        Parameter.TextParameter validValue = new Parameter.TextParameter("ValidValue");

        Exception exception = assertThrows(ValidationException.class, () -> {
            validValue.setValue(emptyString);
        });

        assertEquals("New value for TextParameter is not valid.", exception.getMessage());
    }
}