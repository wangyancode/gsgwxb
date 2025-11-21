package com.jsdc.gsgwxb.exception;

public class UserException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private Integer code;

    private String message;

    public UserException(String message) {
        this.message = message;
    }

    public UserException(String message, Integer code) {
        this.message = message;
        this.code = code;
    }

    public UserException(String message, Throwable e) {
        super(message, e);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }
    
}
