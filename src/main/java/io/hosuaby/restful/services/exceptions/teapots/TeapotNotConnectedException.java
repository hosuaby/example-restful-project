package io.hosuaby.restful.services.exceptions.teapots;

/**
 * Exception thrown when client tries to communicate with teapot that is not
 * connected to server.
 */
public class TeapotNotConnectedException extends Exception {

    /**
     * When teapot with defined id not exists.
     */
    private static final String ERR_TEAPOT_NOT_CONNECTED = "Teapot with id \"%s\" not connected to server";

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = 3484631448010563705L;

    /**
     * Constructor from teapot id.
     *
     * @param teapotId    teapot id
     */
    public TeapotNotConnectedException(String teapotId) {
        super(String.format(ERR_TEAPOT_NOT_CONNECTED, teapotId));
    }

}
