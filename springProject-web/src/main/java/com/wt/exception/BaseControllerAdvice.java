package com.wt.exception;


import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class BaseControllerAdvice {
    private static Logger log = Logger.getLogger(BaseControllerAdvice.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String exception(Exception e, WebRequest request) {
        log.error(e.getMessage(),e);
        return "redirect:/errorPage/500.jsp";
    }

}
