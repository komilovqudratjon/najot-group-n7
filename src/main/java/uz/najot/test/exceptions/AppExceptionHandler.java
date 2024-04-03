package uz.najot.test.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: TODO
 * @date: 31 March 2024 $
 * @time: 4:43 PM 59 $
 * @author: Qudratjon Komilov
 */
@RestControllerAdvice
@Slf4j
public class AppExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

        List<Map<String, String>> errorsList = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            Map<String, String> errors = new HashMap<>();
            errors.put("field", ((FieldError) error).getField());
            errors.put("description", error.getDefaultMessage());
            errorsList.add(errors);
        });


        return errorsList;
    }

    @ExceptionHandler(UserNotFoundEx.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> maps(UserNotFoundEx ex) {
        return Map.of("message", ex.getMessage());
    }


}
