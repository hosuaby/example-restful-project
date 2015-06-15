package io.hosuaby.restful.services.exceptions.teapots;

import io.hosuaby.restful.domain.Teapot;

/**
 * Exception thrown when asked teapot doesn't exist.
 */
public class TeapotNotExistsException extends Exception {

    /**
     * When teapot with defined id not exists.
     */
    private static final String ERR_TEAPOT_WITH_ID_NOT_EXISTS = "Teapot with id \"%s\" not exists";

    /**
     * When defined {@link Teapot} not exists.
     */
    private static final String ERR_THIS_TEAPOT_NOT_EXISTS = "This teapot not exists";

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = 7839587611390496077L;

    /**
     * Constructor from teapot id.
     *
     * @param teapotId    teapot id
     */
    public TeapotNotExistsException(String teapotId) {
        super(String.format(ERR_TEAPOT_WITH_ID_NOT_EXISTS, teapotId));
    }

    /**
     * Constructor from {@link Teapot}.
     *
     * @param teapot    teapot object
     */
    public TeapotNotExistsException(Teapot teapot) {
        super(ERR_THIS_TEAPOT_NOT_EXISTS + ": " + teapot.toString());
    }

}
