package cz.upce.fei.nnptp.validation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringNotEmptyValidationTest {
    @Test
    void stringShouldByInvalidWhenNull() {
        String nullString = null;
        Validation<String> stringNotEmptyValidation = new StringNotEmptyValidation();

        boolean result = stringNotEmptyValidation.valid(nullString);

        assertFalse(result);
    }

    @Test
    void stringShouldByInvalidWhenEmpty() {
        String emptyString = "";
        Validation<String> stringNotEmptyValidation = new StringNotEmptyValidation();

        boolean result = stringNotEmptyValidation.valid(emptyString);

        assertFalse(result);
    }

    @Test
    void stringShouldByValidWhenNotEmpty() {
        String notString = "NotEmpty";
        Validation<String> stringNotEmptyValidation = new StringNotEmptyValidation();

        boolean result = stringNotEmptyValidation.valid(notString);

        assertTrue(result);
    }

}