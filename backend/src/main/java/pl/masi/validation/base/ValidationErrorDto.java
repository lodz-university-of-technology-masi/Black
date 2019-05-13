package pl.masi.validation.base;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ValidationErrorDto {
    private List<ErrorDetailDto> fieldErrors = new ArrayList<>();
    private List<ErrorDetailDto> globalErrors = new ArrayList<>();
}
