package io.hosuaby.restful.controllers;

import io.hosuaby.restful.domain.Teapot;
import io.hosuaby.restful.services.TeapotCRUDService;
import io.hosuaby.restful.services.exceptions.teapots.TeapotAlreadyExistsException;
import io.hosuaby.restful.services.exceptions.teapots.TeapotNotExistsException;
import io.hosuaby.restful.services.exceptions.teapots.TeapotsNotExistException;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for CRUD operations of teapots.
 *
 * @author Alexei KLENIN
 */
@RestController
@RequestMapping("/crud/teapots")
public class TeapotCRUDController {

    /**
     * Teapot CRUD service.
     */
    @Autowired
    private TeapotCRUDService crud;

    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public long count() {
        return crud.count();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(String id) throws TeapotNotExistsException {
        crud.delete(id);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Teapot> findAll(@RequestParam(required = false) String[] ids)
            throws TeapotsNotExistException {
        if (ids != null) {
            return (List<Teapot>) crud.findAll(ids);
        } else {
            return (List<Teapot>) crud.findAll();
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void save(
            @PathVariable String id,
            @RequestBody Teapot teapot) {
        crud.save(teapot);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void add(@RequestBody Teapot teapot)
            throws TeapotAlreadyExistsException {
        crud.add(teapot);
    };

}
