package io.hosuaby.restful.services.exceptions.teapots;

import io.hosuaby.restful.domain.Teapot;

import java.util.Collection;
import java.util.stream.Stream;

/**
 * Exception thrown when one or many asked teapots not exist.
 *
 * @author Alexei KLENIN
 */
public class TeapotsNotExistException extends Exception {

    /**
     * When teapots with defined ids not exist.
     */
    private static final String ERR_TEAPOTS_WITH_IDS_NOT_EXISTS = "Teapots with folowing ids not exist";

    /**
     * When defined {@link Teapot} objects not exist.
     */
    private static final String ERR_THOSE_TEAPOTS_NOT_EXIST = "Those teapots not exist";

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = 3290080700826331780L;

    /**
     * Constructor from collection of teapot ids.
     *
     * @param ids    teapot ids
     */
    public TeapotsNotExistException(Collection<String> ids) {
        super(ERR_TEAPOTS_WITH_IDS_NOT_EXISTS + ": " + String.join(", ", ids));
    }

    /**
     * Constructor from iterable with {@link Teapot} objects.
     *
     * @param teapots    iterable with teapot objects
     */
    public TeapotsNotExistException(Iterable<? extends Teapot> teapots) {
        super(ERR_THOSE_TEAPOTS_NOT_EXIST + ": " +

                /* Funky Java 8 lambdas */
                Stream.of(teapots).map(teapot -> {
                    return teapot.toString();
                }).reduce("", (result, teapotStr) -> {
                    result += "\n" + teapotStr;
                    return result;
                }));
    }

}
