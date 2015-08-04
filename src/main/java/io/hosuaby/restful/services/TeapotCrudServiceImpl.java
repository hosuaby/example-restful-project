package io.hosuaby.restful.services;

import io.hosuaby.restful.domain.Teapot;
import io.hosuaby.restful.repositories.TeapotRepository;
import io.hosuaby.restful.services.exceptions.teapots.TeapotAlreadyExistsException;
import io.hosuaby.restful.services.exceptions.teapots.TeapotNotExistsException;
import io.hosuaby.restful.services.exceptions.teapots.TeapotsAlreadyExistException;
import io.hosuaby.restful.services.exceptions.teapots.TeapotsNotExistException;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@TeapotService} interface.
 */
@Service
// TODO: Add necessary mechanisms for thread safety
public class TeapotCrudServiceImpl implements TeapotCrudService {

    /**
     * Teapot repository.
     */
    @Autowired
    private TeapotRepository teapotRepository;

    /** {@inheritDoc} */
    @Override
    public Teapot find(String id) throws TeapotNotExistsException {
        if (teapotRepository.exists(id)) {
            return teapotRepository.findOne(id);
        } else {
            throw new TeapotNotExistsException(id);
        }
    }

    /** {@inheritDoc} */
    @Override
    public Collection<Teapot> findAll() {
        return teapotRepository.findAll();
    }

    /** {@inheritDoc} */
    @Override
    public Collection<Teapot> findAll(String[] ids)
            throws TeapotsNotExistException {
        Collection<Teapot> found = new HashSet<>();     // found teapots
        Collection<String> notFound = new HashSet<>();  // not found teapots

        Arrays.asList(ids).forEach(id -> {
            if (teapotRepository.exists(id)) {
                found.add(teapotRepository.findOne(id));
            } else {
                notFound.add(id);
            }
        });

        if (!notFound.isEmpty()) {
            throw new TeapotsNotExistException(notFound);
        }

        return found;
    }

    /** {@inheritDoc} */
    @Override
    public boolean exists(String id) {
        return teapotRepository.exists(id);
    }

    /** {@inheritDoc} */
    @Override
    public boolean exists(Teapot teapot) {
        return teapotRepository.exists(teapot.getId());
    }

    /** {@inheritDoc} */
    @Override
    public long count() {
        return teapotRepository.count();
    }

    /** {@inheritDoc} */
    @Override
    public synchronized void add(Teapot teapot)
            throws TeapotAlreadyExistsException {
        if (!teapotRepository.exists(teapot.getId())) {
            teapotRepository.save(teapot);
        } else {
            throw new TeapotAlreadyExistsException(teapot.getId());
        }
    }

    /** {@inheritDoc} */
    @Override
    public synchronized void add(Collection<Teapot> teapots)
            throws TeapotsAlreadyExistException {
        Collection<Teapot> existing = (Collection<Teapot>) teapots
            .stream()
            .filter(teapot -> teapotRepository.equals(teapot.getId()));

        if (!existing.isEmpty()) {
            throw new TeapotsAlreadyExistException(existing);
        }

        teapotRepository.save(teapots);
    }

    /** {@inheritDoc} */
    @Override
    public synchronized void update(String id, Teapot teapot)
            throws TeapotNotExistsException, TeapotAlreadyExistsException {
        if (!teapotRepository.exists(id)) {
            throw new TeapotNotExistsException(id);
        }

        if (!teapot.getId().equals(id)) {
            if (teapotRepository.exists(teapot.getId())) {
                throw new TeapotAlreadyExistsException(teapot.getId());
            }
        }

        teapotRepository.delete(id);
        teapotRepository.save(teapot);
    }

    /** {@inheritDoc} */
    @Override
    public synchronized void delete(String id) throws TeapotNotExistsException {
        if (teapotRepository.exists(id)) {
            teapotRepository.delete(id);
        } else {
            throw new TeapotNotExistsException(id);
        }
    }

    /** {@inheritDoc} */
    @Override
    public synchronized void delete(Teapot teapot) throws TeapotNotExistsException {
        if (teapotRepository.exists(teapot.getId())) {
            teapotRepository.delete(teapot);
        } else {
            throw new TeapotNotExistsException(teapot);
        }
    }

    /** {@inheritDoc} */
    @Override
    public synchronized void delete(String[] ids) throws TeapotsNotExistException {
        Collection<String> notFound =
            (Collection<String>) Arrays.asList(ids)
                .stream()
                .filter(id -> !teapotRepository.exists(id));

        if (!notFound.isEmpty()) {
            throw new TeapotsNotExistException(notFound);
        }

        Arrays.asList(ids).forEach(id -> teapotRepository.delete(id));
    }

    /** {@inheritDoc} */
    @Override
    public synchronized void delete(Collection<Teapot> teapots)
            throws TeapotsNotExistException {
        Collection<? extends Teapot> notFound =
                (Collection<? extends Teapot>) teapots
                    .stream()
                    .filter(teapot -> !teapotRepository.exists(teapot.getId()));

        if (!notFound.isEmpty()) {
            throw new TeapotsNotExistException(notFound);
        }

        teapots.forEach(teapot -> teapotRepository.delete(teapot));
    }

    /** {@inheritDoc} */
    @Override
    public synchronized void deleteAll() {
        teapotRepository.deleteAll();
    }

}
