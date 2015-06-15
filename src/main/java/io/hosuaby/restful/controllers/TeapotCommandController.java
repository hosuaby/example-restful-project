package io.hosuaby.restful.controllers;

import io.hosuaby.restful.domain.Teapot;
import io.hosuaby.restful.services.TeapotCRUDService;
import io.hosuaby.restful.services.exceptions.teapots.TeapotAlreadyExistsException;
import io.hosuaby.restful.services.exceptions.teapots.TeapotNotExistsException;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * API to send commands to teapots.
 */
// TODO: develop this controller
@RestController
@RequestMapping("/actions/teapots")
public class TeapotCommandController {

    /**
     * Teapot CRUD service.
     */
    @Autowired
    private TeapotCRUDService crud;

    /**
     * Random generator.
     */
    private Random random = new Random();

    @RequestMapping(
            value = "/{id}/startup",
            method = RequestMethod.POST)
    public void startup(@PathVariable String id)
            throws TeapotNotExistsException, TeapotAlreadyExistsException,
            UnknownHostException {
        Teapot teapot = crud.find(id);

        Inet4Address ip = (Inet4Address) InetAddress.getByAddress(new byte[] {
                (byte) 192, (byte) 168, (byte) 13, (byte) random.nextInt(255)
        });

        teapot.setIp(ip);
        crud.update(id, teapot);
    }

    @RequestMapping(
            value = "/{id}/shutdown",
            method = RequestMethod.POST)
    public void shutdown(@PathVariable String id)
            throws TeapotNotExistsException, TeapotAlreadyExistsException {
        Teapot teapot = crud.find(id);
        teapot.setIp(null);
        crud.update(id, teapot);
    }

}
