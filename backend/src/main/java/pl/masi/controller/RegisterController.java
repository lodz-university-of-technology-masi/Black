package pl.masi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.masi.entity.User;
import pl.masi.service.UserService;
import pl.masi.utils.RegisterCheck;


@Controller
@RequestMapping(value = "/register")
public class RegisterController {

    @Autowired
    private UserService service;

    @Autowired
    private UserService userService;

    @POST
    @RequestMapping(value = "/register")
    public UserService registerUser(User user, BindingResult result) {

        User userExist = (User) userService.loadUserByUsername(user.getLogin());

        new RegisterCheck().validateEmailExist(userExist, result);

        new RegisterCheck().validate(user, result);

        userService.addUser(user);

return service;
    }
}


