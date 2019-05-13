package pl.masi.validation.base;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorDetailDto {
    private String path;
    private String message;
}
