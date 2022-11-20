package cz.upce.fei.nnptp.validation;

public interface Validation<T> {
    boolean valid(T value);
}
