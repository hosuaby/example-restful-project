package io.hosuaby.restful.services;

import io.hosuaby.restful.domain.Teapot;
import io.hosuaby.restful.services.exceptions.teapots.TeapotAlreadyExistsException;
import io.hosuaby.restful.services.exceptions.teapots.TeapotNotExistsException;
import io.hosuaby.restful.services.exceptions.teapots.TeapotsAlreadyExistException;
import io.hosuaby.restful.services.exceptions.teapots.TeapotsNotExistException;

import java.util.Collection;

/**
 * Provides methods for CRUD operations and remote control operations on
 * teapots.
 *
 * @author Alexei KLENIN
 */
public interface TeapotCRUDService {

    /**
     * Returns teapot found by it's id.
     *
     * @param id    teapot id
     *
     * @return found teapot
     *
     * @throws TeapotNotExistsException
     *      if teapot not found by id
     */
    Teapot find(String id) throws TeapotNotExistsException;

    /**
     * @return all existing teapots.
     */
    Collection<Teapot> findAll();

    /**
     * Returns teapots found by it's ids.
     *
     * @param ids    teapot ids
     *
     * @return found teapots.
     *
     * @throws TeapotsNotExistException
     *      if one or more teapots are not found
     */
    Collection<Teapot> findAll(String[] ids) throws TeapotsNotExistException;

    /**
     * Checks if teapot with defined id exist.
     *
     * @param id    teapot id
     *
     * @return true if teapot with this id exists, false if not
     */
    boolean exists(String id);

    /**
     * Checks if teapot exists in the repository.
     *
     * @param teapot    teapot
     *
     * @return true if teapot with this id exists, false if not
     */
    boolean exists(Teapot teapot);

    /**
     * @return count of existing teapots.
     */
    long count();

    /**
     * Adds a new teapot.
     *
     * @param teapot    {@link Teapot} object
     *
     * @throws TeapotAlreadyExistsException
     *      if defined teapot already exists
     */
    void add(Teapot teapot) throws TeapotAlreadyExistsException;

    /**
     * Adds new teapots into repository. If an existing teapot tries to be added
     * method throws an exception and no one insertion produces.
     *
     * @param teapots    {@link Teapot} objects
     *
     * @throws TeapotsAlreadyExistException
     *      if some of defined teapots already exist
     */
    void add(Collection<Teapot> teapots) throws TeapotsAlreadyExistException;

    /**
     * Updates existing teapot. Can not be used to add a new teapot to the
     * repository.
     *
     * @param id        id of updated teapot
     * @param teapot    new {@link Teapot} object
     *
     * @throws TeapotNotExistsException
     *      if teapot not found by id
     * @throws TeapotAlreadyExistsException
     *      if new teapot enters in conflict with an existing teapot
     */
    void update(String id, Teapot teapot)
            throws TeapotNotExistsException, TeapotAlreadyExistsException;

    /**
     * Deletes a teapot by id.
     *
     * @param id    teapot id
     *
     * @throws TeapotNotExistsException
     *      if teapot with defined id doesn't exist
     */
    void delete(String id) throws TeapotNotExistsException;

    /**
     * Deletes a teapot object.
     *
     * @param teapot    {@link Teapot} object
     *
     * @throws TeapotNotExistsException
     *      if defined teapot doesn't exist
     */
    void delete(Teapot teapot) throws TeapotNotExistsException;

    /**
     * Deletes multiple teapots found by id. If one or more teapots doesn't
     * exist method do not deletes any teapot at all.
     *
     * @param ids    teapot ids
     *
     * @throws TeapotsNotExistException
     *      if one or more of defined teapots doesn't exist
     */
    void delete(String[] ids) throws TeapotsNotExistException;

    /**
     * Deletes multiple teapots. If one or more teapots doesn't exist method do
     * not deletes any teapot at all.
     *
     * @param teapots    {@link Teapot} objects to delete
     *
     * @throws TeapotsNotExistException
     *      if one or more of defined teapots doesn't exist
     */
    void delete(Collection<Teapot> teapots) throws TeapotsNotExistException;

    /**
     * Deletes all existing teapots.
     */
    void deleteAll();

}

