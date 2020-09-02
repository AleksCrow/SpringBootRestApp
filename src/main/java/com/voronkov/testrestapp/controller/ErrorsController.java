package com.voronkov.testrestapp.controller;

import com.voronkov.testrestapp.exception.CustomErrorResponse;
import com.voronkov.testrestapp.exception.UserExceptionHandler;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**Controller for errors endpoints
 * @author A.Voronkov
 * @since 02.09.2020
 * @version 1.0
 */
@RestController
public class ErrorsController implements ErrorController {

    @Override
    public String getErrorPath() {
        return null;
    }

    @GetMapping("/error/last")
    public CustomErrorResponse getLastException() {
        return UserExceptionHandler.error;
    }

}
