package pl.masi.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import pl.masi.entity.TestAnswer;
import pl.masi.validation.base.EntityValidator;

@Component
public class TestAnswerValidator extends EntityValidator<TestAnswer> {

    @Override
    public void validateObj(TestAnswer testAnswer, Errors errors) {
    }
}
