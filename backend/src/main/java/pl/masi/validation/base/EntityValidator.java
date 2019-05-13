package pl.masi.validation.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import pl.masi.entity.base.BaseEntity;

import org.springframework.validation.Validator;

import javax.validation.ConstraintViolation;
import javax.validation.ValidatorFactory;
import java.lang.reflect.ParameterizedType;
import java.util.Set;

public abstract class EntityValidator<ENTITY extends BaseEntity> implements Validator {

    @Autowired
    protected ValidatorFactory validatorFactory;

    // https://stackoverflow.com/questions/3403909/get-generic-type-of-class-at-runtime
    private Class<ENTITY> entityClass =(Class<ENTITY>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];


    @Override
    public boolean supports(Class<?> clazz) {
        return entityClass.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        this.validate((ENTITY) target, errors);
    }

    public abstract void validate(ENTITY entity, Errors errors);

    public void validate(ENTITY entity) {
        // BeanPropertyBindingResult potrzebny jest tylko jako implementacja Errors
        Errors errors = new BeanPropertyBindingResult(entity, "___");

        // Uruchomienie walidacji jsr303, dodanie jej wyników do errors
        Set<ConstraintViolation<ENTITY>> violations = validatorFactory.getValidator().validate(entity);
        for (ConstraintViolation<ENTITY> violation : violations) {
            errors.rejectValue(violation.getPropertyPath().toString(), violation.getMessage());
        }
        // uruchomienie właściwej walidacji
        validate(entity, errors);
        if (errors.hasErrors()) {
            throw new ValidationException(errors);
        }
    }
}
