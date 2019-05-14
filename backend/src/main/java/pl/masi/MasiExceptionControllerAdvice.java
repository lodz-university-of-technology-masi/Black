package pl.masi;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.masi.exception.ValidationException;
import pl.masi.validation.base.ErrorDetailDto;
import pl.masi.validation.base.ValidationErrorDto;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class MasiExceptionControllerAdvice {

    // TODO obsługa wszystkich wyjątków MasiException

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ExceptionHandler({ValidationException.class})
    public @ResponseBody ResponseEntity<ValidationErrorDto> handleValidationErrors(HttpServletRequest req, final ValidationException exception) {
        ValidationErrorDto validationErrorDto = new ValidationErrorDto();
        for (FieldError error : exception.getErrors().getFieldErrors()) {
            ErrorDetailDto detail = new ErrorDetailDto();
            detail.setPath(error.getField());
            detail.setMessage( error.getCode() != null ? error.getCode() : error.getDefaultMessage());
            validationErrorDto.getFieldErrors().add(detail);
        }
        for (ObjectError error : exception.getErrors().getGlobalErrors()) {
            ErrorDetailDto detail = new ErrorDetailDto();
            detail.setMessage(error.getCode());
            validationErrorDto.getGlobalErrors().add(detail);
        }
        return ResponseEntity.badRequest().body(validationErrorDto);
    }
}
