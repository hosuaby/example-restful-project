package io.hosuaby.restful.controllers;

import io.hosuaby.restful.services.exceptions.teapots.TeapotAlreadyExistsException;
import io.hosuaby.restful.services.exceptions.teapots.TeapotNotExistsException;
import io.hosuaby.restful.services.exceptions.teapots.TeapotsAlreadyExistException;
import io.hosuaby.restful.services.exceptions.teapots.TeapotsNotExistException;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception handler for all controllers.
 *
 * @author Alexei KLENIN
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles {@link TeapotAlreadyExistsException}.
     *
     * @param httpResponse    HTTP Servlet Response
     * @param exception       exception
     *
     * @throws IOException
     *      never thrown
     */
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(TeapotAlreadyExistsException.class)
    public void handleTeapotAlreadyExistsException(
            HttpServletResponse httpResponse,
            TeapotAlreadyExistsException exception) throws IOException {
        httpResponse.getWriter().write(exception.getMessage());
    }

    /**
     * Handles {@link TeapotNotExistsException}.
     *
     * @param httpResponse    HTTP Servlet Response
     * @param exception       exception
     *
     * @throws IOException
     *      never thrown
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(TeapotNotExistsException.class)
    public void handleTeapotNotExistsException(
            HttpServletResponse httpResponse,
            TeapotNotExistsException exception) throws IOException {
        httpResponse.getWriter().write(exception.getMessage());
    }

    /**
     * Handles {@link TeapotsAlreadyExistException}.
     *
     * @param httpResponse    HTTP Servlet Response
     * @param exception       exception
     *
     * @throws IOException
     *      never thrown
     */
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(TeapotsAlreadyExistException.class)
    public void handleTeapotsAlreadyExistException(
            HttpServletResponse httpResponse,
            TeapotsAlreadyExistException exception) throws IOException {
        httpResponse.getWriter().write(exception.getMessage());
    }

    /**
     * Handles {@link TeapotsNotExistException}.
     *
     * @param httpResponse    HTTP Servlet Response
     * @param exception       exception
     *
     * @throws IOException
     *      never thrown
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(TeapotsNotExistException.class)
    public void handleTeapotsNotExistException(
            HttpServletResponse httpResponse,
            TeapotsNotExistException exception) throws IOException {
        httpResponse.getWriter().write(exception.getMessage());
    }

}
