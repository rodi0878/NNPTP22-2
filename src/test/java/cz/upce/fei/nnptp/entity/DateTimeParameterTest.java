package cz.upce.fei.nnptp.entity;

import cz.upce.fei.nnptp.exception.ValidationException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DateTimeParameterTest {

    @Test
    void constructorShouldThrowValidationExceptionWhenNullPassed() {
        LocalDateTime nullTime = null;

        Exception exception = assertThrows(ValidationException.class, () -> {
            new Parameter.DateTimeParameter(nullTime);
        });

        assertEquals("Value for DateTimeParameter is not valid.", exception.getMessage());
    }

    @Test
    void setValueShouldThrowValidationExceptionWhenNullPassed() {
        LocalDateTime nullTime = null;
        Parameter.DateTimeParameter validValue = new Parameter.DateTimeParameter(LocalDateTime.now());

        Exception exception = assertThrows(ValidationException.class, () -> {
            validValue.setValue(nullTime);
        });

        assertEquals("New value for DateTimeParameter is not valid.", exception.getMessage());
    }
}