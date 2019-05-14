package pl.masi.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegistrationRequestDto {
    private String login;
    private String password;
    private String email;
    private String language;
}
