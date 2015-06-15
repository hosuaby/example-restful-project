package io.hosuaby.restful.services.exceptions.teapots;

import io.hosuaby.restful.domain.Teapot;

import java.util.Collection;
import java.util.stream.Stream;

/**
 * Exception thrown when user tries to add teapots that already exist.
 */
public class TeapotsAlreadyExistException extends Exception {

    /**
     * When teapots with defined ids already exist.
     */
    private static final String ERR_TEAPOTS_WITH_IDS_ALREADY_EXIST = "Teapots with folowing ids already exist";

    /**
     * When defined {@link Teapot} objects already exist.
     */
    private static final String ERR_THOSE_TEAPOTS_ALREADY_EXIST = "Those teapots already exist";

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = 4483120563431775221L;

    /**
     * Constructor from collection of teapot ids.
     *
     * @param ids    teapot ids
     */
    public TeapotsAlreadyExistException(Collection<String> ids) {
        super(ERR_TEAPOTS_WITH_IDS_ALREADY_EXIST + ": " + String.join(", ", ids));
    }

    /**
     * Constructor from iterable with {@link Teapot} objects.
     *
     * @param teapots    iterable with teapot objects
     */
    public TeapotsAlreadyExistException(Iterable<? extends Teapot> teapots) {
        super(ERR_THOSE_TEAPOTS_ALREADY_EXIST + ": " +

                /* Funky Java 8 lambdas */
                Stream.of(teapots).map(teapot -> {
                    return teapot.toString();
                }).reduce("", (result, teapotStr) -> {
                    result += "\n" + teapotStr;
                    return result;
                }));
    }

}
