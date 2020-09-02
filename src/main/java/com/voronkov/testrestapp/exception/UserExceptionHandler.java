package com.voronkov.testrestapp.exception;

import com.fasterxml.jackson.annotation.JsonView;
import com.voronkov.testrestapp.util.View;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.Timestamp;

/**Class with exception type handler
 * @author A.Voronkov
 * @since 30.08.2020
 * @version 1.0
 */
@RestControllerAdvice
public class UserExceptionHandler extends ResponseEntityExceptionHandler {

    /**static variable for storage last exception
     */
    public static CustomErrorResponse error;

    @JsonView({View.DisplayExceptionMessage.class})
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> handleUserNotFoundException(Exception ex) {
        error = new CustomErrorResponse(new Timestamp(System.currentTimeMillis()), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @JsonView({View.DisplayExceptionMessage.class})
    @ExceptionHandler(EmailValidException.class)
    public ResponseEntity<CustomErrorResponse> handleEmailValidException(Exception ex) {
        error = new CustomErrorResponse(new Timestamp(System.currentTimeMillis()), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @JsonView({View.DisplayExceptionMessage.class})
    @ExceptionHandler(ExistingEmailException.class)
    public ResponseEntity<CustomErrorResponse> handleExistEmailException(Exception ex) {
        error = new CustomErrorResponse(new Timestamp(System.currentTimeMillis()), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }
}
