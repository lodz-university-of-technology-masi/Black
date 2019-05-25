package pl.masi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.masi.dto.RegistrationRequestDto;
import pl.masi.service.UserService;


@Controller
@RequestMapping(value = "/register")
public class RegisterController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Void> registerUser(@RequestBody RegistrationRequestDto register) {
        userService.addUser(register);

        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @PostMapping(path = "/redactor")
    public ResponseEntity<Void> registerRedactor(@RequestBody RegistrationRequestDto register) {

        userService.addRedactor(register);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}


