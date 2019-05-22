package pl.masi.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import pl.masi.entity.Evaluation;
import pl.masi.validation.base.EntityValidator;

@Component
public class EvaluationValidator extends EntityValidator<Evaluation> {

    @Override
    public void validateObj(Evaluation evaluation, Errors errors) {
        if(evaluation.getTestAnswer() == null || evaluation.getTestAnswer().getId() == null) {
            errors.rejectValue("testAnswer", "testAnswer.id cannot be null");
        }
    }
}
