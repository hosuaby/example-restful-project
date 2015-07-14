package io.hosuaby.restful.domain.validators;

import io.hosuaby.restful.domain.Teapot;

import java.util.Arrays;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Teapot validator.
 */
@Component
public class TeapotValidator implements Validator {

    /**
     * Pattern for teapot id. Id must start with letter and can contain letter,
     * digits and "_" character.
     */
    private static final Pattern ID_PATTERN = Pattern.compile("[a-zA-Z]\\w*");

    /**
     * Valid teapot volumes.
     */
    private static final Float[] VALID_VOLUMES = new Float[] {
        Teapot.L0_3, Teapot.L0_5, Teapot.L1, Teapot.L1_5, Teapot.L3, Teapot.L5,
        Teapot.L8, Teapot.L10
    };

    /**
     * Error messages.
     */
    private static final String ERR_ID_UNDEFINED = "Teapot id is undefined";
    private static final String ERR_NAME_UNDEFINED = "Teapot name is undefined";
    private static final String ERR_ID_BAD_FORMAT = "Teapot id has bad format. "
            + "Id must start with letter and can contain letter, digits and "
            + "\"_\" character.";
    private static final String ERR_BAD_VOLUME = "Teapot has a bad volume. "
            + "List of authorized volumes: "
            + StringUtils.arrayToDelimitedString(VALID_VOLUMES, ", ");

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
    public void validate(Object obj, Errors errors) {

        /* Id must be defined */
        ValidationUtils.rejectIfEmptyOrWhitespace(
                errors, "id", "errIdUdefined", ERR_ID_UNDEFINED);

        /* Name must be defined */
        ValidationUtils.rejectIfEmptyOrWhitespace(
                errors, "name", "errNameUndefined", ERR_NAME_UNDEFINED);

        Teapot teapot = (Teapot) obj;

        /* Check id against pattern */
        if (!ID_PATTERN.matcher(teapot.getId()).matches()) {
            errors.rejectValue("id", "errIdBadFormat", ERR_ID_BAD_FORMAT);
        }

        /* Check if volume is in the list of valid volumes */
        if (!Arrays.asList(VALID_VOLUMES).contains(teapot.getVolume())) {
            errors.rejectValue("volume", "errBadVolume", ERR_BAD_VOLUME);
        }
    }

}
