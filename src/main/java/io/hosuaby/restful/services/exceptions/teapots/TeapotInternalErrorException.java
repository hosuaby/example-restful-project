package io.hosuaby.restful.services.exceptions.teapots;


/**
 * Exception thrown when client asks teapot to execute unsupported command.
 */
public class TeapotInternalErrorException extends Exception {

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = 5083886954829581207L;

    /**
     * Constructor from error message.
     *
     * @param error    error message
     */
    public TeapotInternalErrorException(String error) {
        super(error);
    }
}
