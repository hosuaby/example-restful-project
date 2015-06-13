package io.hosuaby.restful.services.exceptions.teapots;

/**
 * Exception thrown when user tries to add teapot that already exists.
 *
 * @author Alexei KLENIN
 */
public class TeapotAlreadyExistsException extends Exception {

    /**
     * When teapot with defined id already exists.
     */
    private static final String ERR_TEAPOT_WITH_ID_ALREADY_EXISTS = "Teapot with id \"%s\" already exists";

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = -8796165842297526641L;

    /**
     * Constructor from teapot id.
     *
     * @param teapotId    teapot id
     */
    public TeapotAlreadyExistsException(String teapotId) {
        super(String.format(ERR_TEAPOT_WITH_ID_ALREADY_EXISTS, teapotId));
    }

}
