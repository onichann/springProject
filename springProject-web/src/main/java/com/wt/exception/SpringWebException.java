package com.wt.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_GATEWAY,reason = "Spring项目出现异常")
public class SpringWebException extends  RuntimeException {


    private static final long serialVersionUID = 8991107762244529471L;

    public SpringWebException() {

    }

    public SpringWebException(String message) {
        super(message);
    }

    public SpringWebException(String message, Throwable cause) {
        super(message, cause);
    }

    public SpringWebException(Throwable cause) {
        super(cause);
    }

    public SpringWebException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
