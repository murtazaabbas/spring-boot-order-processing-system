package com.melitaltd.exception;

import org.springframework.http.HttpStatus;

public interface RestError {

    String error();

    HttpStatus httpStatus();

    String description();
}
