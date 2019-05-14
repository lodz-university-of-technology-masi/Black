package pl.masi.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegistrationReqestDto {
    private  String Login;
    private  String password;
    private  String Email;
    private String language;


}
