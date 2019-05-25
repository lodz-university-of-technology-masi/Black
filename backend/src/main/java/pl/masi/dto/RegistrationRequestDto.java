package pl.masi.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Setter
@Getter
public class RegistrationRequestDto {

    @NotEmpty
    private String login;

    @NotEmpty
    private String password;

    @NotEmpty
    private String email;

    @NotEmpty
    private String language;
}
