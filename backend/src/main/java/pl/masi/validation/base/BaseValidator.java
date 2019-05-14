package pl.masi.validation.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import pl.masi.exception.ValidationException;

import javax.validation.ConstraintViolation;
import javax.validation.ValidatorFactory;
import java.lang.reflect.ParameterizedType;
import java.util.Set;

public abstract class BaseValidator<T> implements Validator {

    @Autowired
    protected ValidatorFactory validatorFactory;

    // https://stackoverflow.com/questions/3403909/get-generic-type-of-class-at-runtime
    private Class<T> entityClass =(Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    public abstract void validateObj(T obj, Errors errors);

    @Override
    public boolean supports(Class<?> clazz) {
        return entityClass.equals(clazz);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        this.validateObj((T) target, errors);
    }

    public void validate(T entity) {
        // BeanPropertyBindingResult potrzebny jest tylko jako implementacja Errors
        Errors errors = new BeanPropertyBindingResult(entity, "___");

        // Uruchomienie walidacji jsr303, dodanie jej wyników do errors
        Set<ConstraintViolation<T>> violations = validatorFactory.getValidator().validate(entity);
        for (ConstraintViolation<T> violation : violations) {
            errors.rejectValue(violation.getPropertyPath().toString(), violation.getMessage());
        }
        // uruchomienie właściwej walidacji
        validate(entity, errors);
        if (errors.hasErrors()) {
            throw new ValidationException(errors);
        }
    }
}
