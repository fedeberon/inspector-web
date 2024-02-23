package com.ideaas.api.error;

import com.ideaas.services.bean.ApiError;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;


@ControllerAdvice
public class GlobalExceptionHandler  extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity customHandleNotFound(Exception ex, WebRequest request) {
        LOGGER.error("[ERROR-GEOPLANING] Handled Error by geoplanning application. ", ex);
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }


    @ExceptionHandler(HibernateException.class)
    public ResponseEntity<Object> dataBaseExeption(Exception ex, WebRequest request) {
        LOGGER.error("Handled Error by geoplanning application. ", ex);
        String bodyOfResponse = "[ERROR-GEOPLANING] from $REQUEST: ".concat(request.toString()).concat("\n $EXECPTION:" .concat(ex.toString()));
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiError> entityNotFoundException(Exception ex, WebRequest request) {
        LOGGER.error("Handled Error by geoplanning application. ", ex);
        ApiError error = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "[ERROR-GEOPLANING] *Database error data", ex);

        return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
