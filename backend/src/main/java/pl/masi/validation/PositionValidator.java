package pl.masi.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import pl.masi.entity.Position;
import pl.masi.validation.base.EntityValidator;

@Component
public class PositionValidator extends EntityValidator<Position> {

    @Override
    public void validateObj(Position position, Errors errors) {
    }
}
