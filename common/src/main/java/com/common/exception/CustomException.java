package com.common.exception;

public class CustomException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private final String message;
    private final int errorCode;

    public CustomException(String message, int errorCode) {
        this.message = message;
        this.errorCode = errorCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public int getErrorCode() {
        return errorCode;
    }
}