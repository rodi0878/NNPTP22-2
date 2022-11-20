package cz.upce.fei.nnptp.validation;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorTest {

    @Test
    void validatorShouldReturnFalseWhenValidationReturnFalse() {
        Object nullObject = null;
        Validator<Object> objectValidator = new Validator<>(new NonNullValidation());

        boolean result = objectValidator.valid(nullObject);

        assertFalse(result);
    }

    @Test
    void validatorShouldReturnTrueWhenValidationReturnTrue() {
        Object nonObject = new Object();
        Validator<Object> objectValidator = new Validator<>(new NonNullValidation());

        boolean result = objectValidator.valid(nonObject);

        assertTrue(result);
    }

    @Test
    void validatorShouldReturnFalseWhenAtLeastOneValidationReturnFalse() {
        String emptyString = "";
        Validator<String> stringValidator = new Validator<>(List.of(new NonNullValidation(), new StringNotEmptyValidation()));

        boolean result = stringValidator.valid(emptyString);

        assertFalse(result);
    }
}