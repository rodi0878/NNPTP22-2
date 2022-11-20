package cz.upce.fei.nnptp.validation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NonNullValidationTest {

    @Test
    void objectShouldByInvalidWhenNull() {
        Object nullObject = null;
        NonNullValidation nonNullValidation = new NonNullValidation();

        boolean result = nonNullValidation.valid(nullObject);

        assertFalse(result);
    }

    @Test
    void objectShouldByValidWithAnyNonNullValue() {
        Object nonNullObject = new Object();
        NonNullValidation nonNullValidation = new NonNullValidation();

        boolean result = nonNullValidation.valid(nonNullObject);

        assertTrue(result);
    }
}