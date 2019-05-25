package pl.masi.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.masi.entity.Evaluation;
import pl.masi.entity.User;

import javax.validation.ValidationException;

@Controller
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private EmailService emailService;


    @PostMapping
    public void sendEmail( BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new ValidationException("Brak oceny");
        }
        User user=  ;
        Evaluation evaluation=    ;

        emailService.sendEmail(user.getEmail,evaluation.getContent);




    }
}