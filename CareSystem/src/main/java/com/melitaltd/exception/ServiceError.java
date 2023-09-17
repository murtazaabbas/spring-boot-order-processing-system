package com.melitaltd.exception;

import org.springframework.http.HttpStatus;

public enum ServiceError implements RestError {

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, ""),
    REQUEST_VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "Invalid expected input"),
    DATA_NOT_AVAILABLE(HttpStatus.NOT_FOUND, "No order data available"),
    NOT_AUTHORIZED_ERROR(HttpStatus.UNAUTHORIZED, "Unauthorized user");

    private HttpStatus httpStatus;

    private String description;


    private ServiceError(final HttpStatus httpStatus, final String description) {
        this.httpStatus = httpStatus;
        this.description = description;
    }

    @Override
    public String error() {
        return this.name();
    }

    @Override
    public HttpStatus httpStatus() {
        return this.httpStatus;
    }

    @Override
    public String description() {
        return this.description;
    }

    public RestException buildException() {
        return new ServiceException(this);
    }

    public RestException buildException(String message) {
        this.description = message;
        return new ServiceException(this);
    }

    public RestException buildException(int statusCode, String message) {
        this.description = message;
        this.httpStatus = HttpStatus.valueOf(statusCode);
        return new ServiceException(this);
    }
}
