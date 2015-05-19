package io.hosuaby.restful.repositories;

import io.hosuaby.restful.domain.Teapot;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Map based repository for teapots. No exceptions are thrown at the repository
 * level. This repository is not thread safe. Thread safe must be assured at
 * service layer.
 *
 * @author Alexei KLENIN
 */
@Repository
public class TeapotRepository implements CrudRepository<Teapot, String> {

    /**
     * Map store for the teapots.
     */
    Map<String, Teapot> teapotStore;

    /**
     * Default constructor.
     */
    public TeapotRepository() {
        teapotStore = new HashMap<>();
    }

    /** {@inheritDoc} */
    @Override
    public long count() {
        return teapotStore.size();
    }

    /** {@inheritDoc} */
    @Override
    public void delete(String id) {
        teapotStore.remove(id);
    }

    /** {@inheritDoc} */
    @Override
    public void delete(Teapot teapot) {
        teapotStore.remove(teapot.getId());
    }

    /** {@inheritDoc} */
    @Override
    public void delete(Iterable<? extends Teapot> teapots) {
        for (Teapot teapot : teapots) {
            teapotStore.remove(teapot.getId());
        }
    }

    /** {@inheritDoc} */
    @Override
    public void deleteAll() {
        teapotStore.clear();
    }

    /** {@inheritDoc} */
    @Override
    public boolean exists(String id) {
        return teapotStore.containsKey(id);
    }

    /** {@inheritDoc} */
    @Override
    public Iterable<Teapot> findAll() {
        return teapotStore.values();
    }

    /** {@inheritDoc} */
    @Override
    public Iterable<Teapot> findAll(Iterable<String> ids) {
        Set<Teapot> teapots = new HashSet<>();
        for (String id : ids) {
            Teapot teapot = teapotStore.get(id);
            if (teapot != null) {
                teapots.add(teapot);
            }
        }
        return teapots;
    }

    /** {@inheritDoc} */
    @Override
    public Teapot findOne(String id) {
        return teapotStore.get(id);
    }

    /** {@inheritDoc} */
    @Override
    public <S extends Teapot> S save(S teapot) {
        return (S) teapotStore.put(teapot.getId(), teapot);
    }

    /** {@inheritDoc} */
    @Override
    public <S extends Teapot> Iterable<S> save(Iterable<S> teapots) {
        Set<S> savedTeapots = new HashSet<>();
        for (S teapot : teapots) {
            savedTeapots.add((S) teapotStore.put(teapot.getId(), teapot));
        }
        return savedTeapots;
    }

}
