package pl.masi.validation.base;

import org.springframework.validation.Errors;

public class ValidationException extends RuntimeException {

    private Errors errors;

    public ValidationException(Errors errors) {
        this.errors = errors;
    }

    public Errors getErrors() {
        return errors;
    }

    public void setErrors(Errors errors) {
        this.errors = errors;
    }
}
