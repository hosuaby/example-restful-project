package io.hosuaby.restful.controllers;

import io.hosuaby.restful.PortHolder;
import io.hosuaby.restful.domain.Teapot;
import io.hosuaby.restful.services.TeapotCrudService;
import io.hosuaby.restful.services.TeapotCommandService;
import io.hosuaby.restful.services.exceptions.teapots.TeapotNotConnectedException;
import io.hosuaby.restful.services.exceptions.teapots.TeapotNotExistsException;
import io.hosuaby.restful.simulators.TeapotSimulator;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.DeploymentException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

/**
 * API to send commands to teapots.
 */
// TODO: develop this controller
@RestController
@RequestMapping("/actions/teapots")
public class TeapotCommandController {

    /** Teapot CRUD service */
    @Autowired
    private TeapotCrudService crud;

    /** Teapot command service */
    @Autowired
    private TeapotCommandService commandService;

    /** Port holder */
    @Autowired
    private PortHolder portHolder;

    @RequestMapping(
            value = "/{id}/startup",
            method = RequestMethod.POST)
    public void startup(@PathVariable String id)
            throws TeapotNotExistsException, URISyntaxException,
                DeploymentException, IOException {

        /* Get teapot */
        Teapot teapot = crud.find(id);

        /* Start teapot simulator */
        TeapotSimulator simulator = new TeapotSimulator(
                teapot, portHolder.getPort());
    }

    @RequestMapping(
            value = "/{id}/shutdown",
            method = RequestMethod.POST)
    public void shutdown(@PathVariable String id)
            throws IOException, TeapotNotConnectedException {
        commandService.shutdown(id);
    }

    @RequestMapping(
            value = "/{teapotId}/cli",
            method = RequestMethod.POST)
    public DeferredResult<String> executeCommand(
            @PathVariable String teapotId,
            @RequestParam(required = true) String cmd,
            HttpServletRequest req) throws IOException {
        return commandService.sendMessage(req, teapotId, cmd);
    }

}
