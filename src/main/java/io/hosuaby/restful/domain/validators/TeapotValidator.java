package io.hosuaby.restful.domain.validators;

import io.hosuaby.restful.domain.Teapot;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Teapot validator.
 *
 * @author Alexei KLENIN
 */
@Component
public class TeapotValidator implements Validator {

    /**
     * Supports only {@link Teapot} objects;
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return Teapot.class.equals(clazz);
    }

    /**
     * Validates or rejects the {@link Teapot} object.
     */
    @Override
    public void validate(Object teapot, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "id", "errIdUdefined", "Teapot id is undefined");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "errNameUndefined", "Teapot name is undefined");
    }

}
