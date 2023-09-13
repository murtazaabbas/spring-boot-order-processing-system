package com.melitaltd.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.stream.Collectors;

@ControllerAdvice
public class ServiceExceptionHandler extends ResponseEntityExceptionHandler {

    private ObjectError error;

    @ExceptionHandler(ServiceException.class)
    public final ResponseEntity<ServiceErrorResponse> handleException(final RestException exception) {
        final RestError restError = exception.getRestError();
        final ServiceErrorResponse errorResponse = ServiceErrorResponse.builder().description(restError.description())
                .error(restError.error()).status(restError.httpStatus().name()).build();
        return new ResponseEntity<>(errorResponse, restError.httpStatus());
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ServiceErrorResponse> handleAllException(final Exception ex) {
        final ServiceError serviceError = ServiceError.INTERNAL_SERVER_ERROR;
        final ServiceErrorResponse errorDetails = new ServiceErrorResponse(ex.getMessage(), serviceError.toString(),
                serviceError.httpStatus().toString());
        return new ResponseEntity<>(errorDetails, serviceError.httpStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        final ServiceError serviceError = ServiceError.REQUEST_VALIDATION_ERROR;
        final ServiceErrorResponse errorDetails = new ServiceErrorResponse(ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(error -> error.getDefaultMessage()).collect(Collectors.joining(", ")),
                serviceError.toString(),
                serviceError.httpStatus().toString());
        return new ResponseEntity<>(errorDetails, serviceError.httpStatus());
    }
}
