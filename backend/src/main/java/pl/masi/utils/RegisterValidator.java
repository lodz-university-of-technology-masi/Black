package pl.masi.utils;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import pl.masi.dto.RegistrationReqestDto;
import pl.masi.entity.User;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterValidator implements Validator {

    public static final String EMAIL_PATTERN = "^[a-zA-z0-9]+[\\._a-zA-Z0-9]*@[a-zA-Z0-9]+{2,}\\.[a-zA-Z]{2,}[\\.a-zA-Z0-9]*$";

    public static final String PASSWORD_PATTERN = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[\\!\\@\\#\\$\\*])(?!.*\\s).{8,12}$";

    public static boolean checkEmailOrPassword(String pattern, String pStr) {

        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(pStr);
        return m.matches();
    }

    @Override
    public boolean supports(Class<?> cls) {
        return User.class.equals(cls);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        RegistrationReqestDto u = (RegistrationReqestDto) obj;

        ValidationUtils.rejectIfEmpty(errors, "login", "error.userName.empty");
        ValidationUtils.rejectIfEmpty(errors, "password", "error.userPassword.empty");
        ValidationUtils.rejectIfEmpty(errors, "email", "error.userEmail.empty");
        ValidationUtils.rejectIfEmpty(errors, "language", "error.language.empty");


        if (u.getEmail() != null) {
            boolean isMatch = checkEmailOrPassword(EMAIL_PATTERN, u.getEmail());
            if (!isMatch) {
                errors.rejectValue("email", "error.userEmailIsNotMatch");
            }
        }

        if (u.getPassword() != null) {
            boolean isMatch = checkEmailOrPassword(PASSWORD_PATTERN, u.getPassword());
            if (!isMatch) {
                errors.rejectValue("password", "error.userPasswordIsNotMatch");
            }
        }

    }

    public void validateEmailExist(Optional<User> user, Errors errors) {
        if (user.isPresent()) {
            errors.rejectValue("email", "error.userEmailExist");
        }
    }
}
