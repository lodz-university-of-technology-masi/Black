package pl.masi.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import pl.masi.dto.RegistrationRequestDto;
import pl.masi.entity.User;
import pl.masi.repository.UserRepository;
import pl.masi.validation.base.BaseValidator;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class RegisterValidator extends BaseValidator<RegistrationRequestDto> {

    @Autowired
    private UserRepository userRepository;

    public static final String EMAIL_PATTERN = "^[a-zA-z0-9]+[\\._a-zA-Z0-9]*@[a-zA-Z0-9]+{2,}\\.[a-zA-Z]{2,}[\\.a-zA-Z0-9]*$";

    //    Validates a strong password. It must be between 8 and 10 characters, contain at least one digit and one alphabetic character,
    //    and must not contain special characters
//    public static final String PASSWORD_PATTERN = "(?!^[0-9]*$)(?!^[a-zA-Z]*$)^([a-zA-Z0-9]{8,10})$";
    public static final String PASSWORD_PATTERN = ".*";

    public static boolean checkEmailOrPassword(String pattern, String pStr) {

        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(pStr);
        return m.matches();
    }

    @Override
    public void validateObj(RegistrationRequestDto obj, Errors errors) {
        Optional<User> oldUser = userRepository.findByLogin(obj.getLogin());
        loginExists(oldUser, errors);

        if (obj.getEmail() != null) {
            boolean isMatch = checkEmailOrPassword(EMAIL_PATTERN, obj.getEmail());
            if (!isMatch) {
                errors.rejectValue("email", "Illegal email format");
            }
        }

        if (obj.getPassword() != null) {
            boolean isMatch = checkEmailOrPassword(PASSWORD_PATTERN, obj.getPassword());
            if (!isMatch) {
                errors.rejectValue("password", "Password too weak");
            }
        }
    }

    @Override
    public boolean supports(Class<?> cls) {
        return User.class.equals(cls);
    }

    public void loginExists(Optional<User> user, Errors errors) {
        if (user.isPresent()) {
            errors.rejectValue("login", "Already exists");
        }
    }
}
