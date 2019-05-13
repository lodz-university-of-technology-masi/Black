package pl.masi.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import pl.masi.entity.Test;
import pl.masi.validation.base.EntityValidator;

@Component
public class TestValidator extends EntityValidator<Test> {

    @Override
    public void validate(Test test, Errors errors) {
    }
}
