package pl.masi.email;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.ValidationException;

@Controller
@RequestMapping("/email")
public class EmailController {


    @PostMapping
    public void sendEmail( BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new ValidationException("Brak oceny");
        }



    }
}