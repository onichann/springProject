package com.wt.model;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("returnJson")
@Scope("prototype")
public class ReturnJson {
    private boolean success;
    private String  message;
    private Object  Data;
    private int code;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return Data;
    }

    public void setData(Object data) {
        Data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
