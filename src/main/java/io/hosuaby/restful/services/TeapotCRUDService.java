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
     * @return count of existing teapots.
     */
    long count();

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
    void delete(Collection<? extends Teapot> teapots)
            throws TeapotsNotExistException;

    /**
     * Deletes all existing teapots.
     */
    void deleteAll();

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
    Collection<Teapot> findAll(String[] ids)
            throws TeapotsNotExistException;

    /**
     * Checks if teapot with defined id exist.
     *
     * @param id    teapot id
     *
     * @return true if teapot with this id exists, false if not
     */
    boolean exists(String id);

    /**
     * Saves a teapot into repository. If teapot doesn't exist adds it. If
     * teapot exists replaces it with a new one.
     *
     * @param teapot    {@link Teapot} object
     */
    <S extends Teapot> void save(S teapot);

    /**
     * Saves teapots into repository. Add teapots that doesn't exist and replace
     * existing ones.
     *
     * @param teapots    {@link Teapot} objects
     */
    <S extends Teapot> void save(Collection<S> teapots);

    /**
     * Adds a new teapot into repository.
     *
     * @param teapot    {@link Teapot} object
     *
     * @throws TeapotAlreadyExistsException
     *      if defined teapot already exists
     */
    <S extends Teapot> void add(S teapot) throws TeapotAlreadyExistsException;

    /**
     * Adds new teapots into repository. If an existing teapot tries to be added
     * method throws an exception and no one insertion produces.
     *
     * @param teapots    {@link Teapot} objects
     *
     * @throws TeapotsAlreadyExistException
     *      if some of defined teapots already exist
     */
    <S extends Teapot> void add(Collection<S> teapots)
            throws TeapotsAlreadyExistException;

}

