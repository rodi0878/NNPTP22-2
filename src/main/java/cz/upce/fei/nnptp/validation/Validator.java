package cz.upce.fei.nnptp.validation;

import java.util.ArrayList;
import java.util.List;

public class Validator<T> {
    public final List<Validation<? super T>> validations = new ArrayList<>();

    public Validator(List<Validation<? super T>> validations) {
        this.validations.addAll(validations);
    }

    public Validator(Validation<? super T> validations) {
        this(List.of(validations));
    }

    public Validator() {
    }

    public boolean valid(T value) {
        return validations.stream().allMatch(tValidation -> tValidation.valid(value));
    }
}
