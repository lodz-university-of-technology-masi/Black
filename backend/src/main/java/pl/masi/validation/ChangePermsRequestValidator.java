package pl.masi.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import pl.masi.dto.ChangePermsRequestDto;
import pl.masi.entity.User;
import pl.masi.repository.UserRepository;
import pl.masi.validation.base.BaseValidator;

import java.util.Optional;

@Component
public class ChangePermsRequestValidator extends BaseValidator<ChangePermsRequestDto> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void validateObj(ChangePermsRequestDto req, Errors errors) {
        Optional<User> user = userRepository.findById(req.getUserId());

        if (user.isPresent()){
            if (user.get().getRole() == User.Role.CANDIDATE
                    && req.getPermission() == ChangePermsRequestDto.Permission.ALL
                    && req.getOperation() == ChangePermsRequestDto.Operation.GRANT) {
                errors.reject("Cannot grant candidate write permissions");
            }
        } else {
            errors.reject("User not found");
        }
    }
}
