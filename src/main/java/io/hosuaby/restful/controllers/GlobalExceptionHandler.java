package io.hosuaby.restful.controllers;

import io.hosuaby.restful.domain.validators.TeapotValidator;
import io.hosuaby.restful.services.exceptions.teapots.TeapotAlreadyExistsException;
import io.hosuaby.restful.services.exceptions.teapots.TeapotInternalErrorException;
import io.hosuaby.restful.services.exceptions.teapots.TeapotNotConnectedException;
import io.hosuaby.restful.services.exceptions.teapots.TeapotNotExistsException;
import io.hosuaby.restful.services.exceptions.teapots.TeapotsAlreadyExistException;
import io.hosuaby.restful.services.exceptions.teapots.TeapotsNotExistException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception handler for all controllers.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Internal server error message.
     */
    private static final String ERR_INTERNAL_SERVER_ERROR = "Internal server error";

    /**
     * Teapot validator.
     */
    @Autowired
    private TeapotValidator teapotValidator;

    /**
     * Adds {@link TeapotValidator} to WebDataBinder.
     *
     * @param binder    WebDataBinder
     */
    @InitBinder
    public void dataBinding(WebDataBinder binder) {
        binder.addValidators(teapotValidator);
    }

    /**
     * Handles {@link TeapotAlreadyExistsException}.
     *
     * @param exception    exception
     *
     * @return response entity
     */
    @ExceptionHandler(TeapotAlreadyExistsException.class)
    public ResponseEntity<String> handleTeapotAlreadyExistsException(
            TeapotAlreadyExistsException exception) {
        return new ResponseEntity<String>(
                exception.getMessage(),
                HttpStatus.CONFLICT);
    }

    /**
     * Handles {@link TeapotNotExistsException}.
     *
     * @param exception    exception
     *
     * @return response entity
     */
    @ExceptionHandler(TeapotNotExistsException.class)
    public ResponseEntity<String> handleTeapotNotExistsException(
            TeapotNotExistsException exception) {
        return new ResponseEntity<String>(
                exception.getMessage(),
                HttpStatus.NOT_FOUND);
    }

    /**
     * Handles {@link TeapotsAlreadyExistException}.
     *
     * @param exception    exception
     *
     * @return response entity
     */
    @ExceptionHandler(TeapotsAlreadyExistException.class)
    public ResponseEntity<String> handleTeapotsAlreadyExistException(
            TeapotsAlreadyExistException exception) {
        return new ResponseEntity<String>(
                exception.getMessage(),
                HttpStatus.CONFLICT);
    }

    /**
     * Handles {@link TeapotsNotExistException}.
     *
     * @param exception    exception
     *
     * @return response entity
     */
    @ExceptionHandler(TeapotsNotExistException.class)
    public ResponseEntity<String> handleTeapotsNotExistException(
            TeapotsNotExistException exception) {
        return new ResponseEntity<String>(
                exception.getMessage(),
                HttpStatus.NOT_FOUND);
    }

    /**
     * Handles {@link TeapotNotConnectedException}.
     *
     * @param exception    exception
     *
     * @return response entity
     */
    @ExceptionHandler(TeapotNotConnectedException.class)
    public ResponseEntity<String> handleTeapotNotConnectedException(
            TeapotNotConnectedException exception) {
        return new ResponseEntity<String>(
                exception.getMessage(),
                HttpStatus.NOT_FOUND);
    }

    /**
     * Handles validation errors.
     *
     * @param exception    exception
     *
     * @return response entity
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExeption(
            MethodArgumentNotValidException exception) {
        StringBuilder sb = new StringBuilder("Validation errors:");

        for (ObjectError error : exception.getBindingResult().getAllErrors()) {
            sb.append("\n\t- ").append(error.getDefaultMessage());
        }

        return new ResponseEntity<String>(
                sb.toString(),
                HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles internal teapot errors provoked by unsupported command.
     *
     * @param exception    exception
     *
     * @return response entity
     */
    @ExceptionHandler(TeapotInternalErrorException.class)
    public ResponseEntity<String> handleTeapotInternalErrorException(
            TeapotInternalErrorException exception) {
        return new ResponseEntity<String>(
                exception.getMessage(),
                HttpStatus.I_AM_A_TEAPOT);  // I am a teapot!
    }

    /**
     * Default handler for unhandled exceptions.
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(
            code = HttpStatus.INTERNAL_SERVER_ERROR,
            reason = ERR_INTERNAL_SERVER_ERROR)
    public void handleOtherExceptions(Exception exception) {
        // TODO: replace by logger
        exception.printStackTrace();
    }

}
