package cz.upce.fei.nnptp.validation;

import java.util.Objects;

public class StringNotEmptyValidation implements Validation<String> {
    @Override
    public boolean valid(String value) {
        return Objects.nonNull(value) && !value.isEmpty();
    }
}
