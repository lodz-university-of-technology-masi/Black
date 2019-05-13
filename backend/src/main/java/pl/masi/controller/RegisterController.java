package pl.masi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.masi.entity.User;
import pl.masi.service.UserService;
import pl.masi.utils.RegisterValidator;
import pl.masi.validation.base.ValidationException;

import java.util.Optional;


@Controller
@RequestMapping(value = "/register")
public class RegisterController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Void> registerUser(@RequestBody User user, BindingResult result) {

        Optional<User> oldUser = userService.getByLogin(user.getLogin());

        new RegisterValidator().validateEmailExist(oldUser, result);

        new RegisterValidator().validate(user, result);

        if (result.hasErrors()) {
            throw new ValidationException(result);
        }

        userService.addUser(user);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}


