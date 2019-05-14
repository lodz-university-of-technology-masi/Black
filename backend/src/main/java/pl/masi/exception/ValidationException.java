package pl.masi.exception;

import org.springframework.validation.Errors;
import pl.masi.exception.MasiException;

public class ValidationException extends MasiException {

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
