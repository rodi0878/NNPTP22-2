package cz.upce.fei.nnptp.validation;

import java.util.Objects;

public class NonNullValidation implements Validation<Object> {

    @Override
    public boolean valid(Object value) {
        return Objects.nonNull(value);
    }
}
