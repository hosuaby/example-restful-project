package io.hosuaby.restful.controllers;

import io.hosuaby.restful.PortHolder;
import io.hosuaby.restful.domain.Teapot;
import io.hosuaby.restful.services.TeapotCRUDService;
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
@RestController
@RequestMapping("/teapots/{id}")
// TODO: find the way to use rare HTTP methods like HEAD, OPTIONS, etc.
public class TeapotCommandController {

    /** Teapot CRUD service */
    @Autowired
    private TeapotCRUDService crud;

    /** Teapot command service */
    @Autowired
    private TeapotCommandService commandService;

    /** Port holder */
    @Autowired
    private PortHolder portHolder;

    /**
     * Launches the simulator of the teapot.
     *
     * @param id    teapot id
     *
     * @throws TeapotNotExistsException
     *      asked teapot was not found
     * @throws URISyntaxException
     * @throws DeploymentException
     * @throws IOException
     */
    @RequestMapping(
            value = "/startup",
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

    /**
     * Shuts down the launched teapot simulator.
     *
     * @param id    teapot id
     *
     * @throws TeapotNotConnectedException
     *      teapot with specified id in not connected
     * @throws IOException
     */
    @RequestMapping(
            value = "/shutdown",
            method = RequestMethod.POST)
    public void shutdown(@PathVariable String id)
            throws IOException, TeapotNotConnectedException {
        commandService.shutdown(id);
    }

    /**
     * Lists files existing on the teapot.
     *
     * @param id     teapot id
     * @param req    HTTP request object
     *
     * @return list of existing files
     */
    @RequestMapping(
            value = "/files",
            method = RequestMethod.GET)
    public DeferredResult<String> listFiles(
            @PathVariable String id,
            HttpServletRequest req) {
        return commandService.sendMessage(req, id, "ls");
    }

    /**
     * Creates a new file on the teapot's file system. If file with the same
     * name already exists, it will be rewritten with a new empty file.
     *
     * @param id          teapot id
     * @param filename    filename
     * @param req         HTTP request object
     *
     * @return asynchronous result that tells that file was successfully created
     */
    @RequestMapping(
            value = "/files/{filename}",
            method = RequestMethod.POST)
    public DeferredResult<String> createFile(
            @PathVariable String id,
            @PathVariable String filename,
            HttpServletRequest req) {
        return commandService.sendMessage(req, id, "touch " + filename);
    }

    /**
     * Removes the file from teapot's file system.
     *
     * @param id          teapot id
     * @param filename    filename
     * @param req         HTTP request object
     *
     * @return asynchronous result that tells that file was successfully removed
     */
    @RequestMapping(
            value = "/files/{filename}",
            method = RequestMethod.DELETE)
    public DeferredResult<String> removeFile(
            @PathVariable String id,
            @PathVariable String filename,
            HttpServletRequest req) {
        return commandService.sendMessage(req, id, "rm " + filename);
    }

    /**
     * Renames the file.
     *
     * @param id             teapot id
     * @param newFilename    new filename
     * @param oldFilename    old filename
     * @param req            HTTP request object
     *
     * @return asynchronous result that tells that file was successfully renamed
     */
    @RequestMapping(
            value = "/files/{newFilename}",
            method = RequestMethod.POST,
            params = { "oldFilename" })
    public DeferredResult<String> renameFile(
            @PathVariable String id,
            @PathVariable String newFilename,
            @RequestParam(value = "oldFilename", required = true) String oldFilename,
            HttpServletRequest req) {
        return commandService.sendMessage(req, id,
                "mv " + oldFilename + " " + newFilename);
    }

    /**
     * Returns the content of file.
     *
     * @param id          teapot id
     * @param filename    filename
     * @param req         HTTP request object
     *
     * @return content of file
     */
    @RequestMapping(
            value = "/files/{filename}",
            method = RequestMethod.GET)
    public DeferredResult<String> getFileContent(
            @PathVariable String id,
            @PathVariable String filename,
            HttpServletRequest req) {
        return commandService.sendMessage(req, id, "cat " + filename);
    }

    /**
     * Writes a new content into the file. File not need exist prior call of
     * this function. If file exists, it's content will be rewritten. If not
     * file will be created with provided content.
     *
     * @param id          teapot id
     * @param filename    filename
     * @param content     content to write
     * @param req         HTTP request object
     *
     * @return asynchronous result that tells that file content was successfully
     * written
     */
    @RequestMapping(
            value = "/files/{filename}",
            method = RequestMethod.PUT)
    public DeferredResult<String> writeFileContent(
            @PathVariable String id,
            @PathVariable String filename,
            @RequestParam(value = "content", required = true) String content,
            HttpServletRequest req) {
        return commandService.sendMessage(req, id,
                "cat " + filename + " " + content);
    }

    /**
     *
     *
     * @param id
     * @param cmd
     * @param req
     * @return
     * @throws IOException
     */
//    @RequestMapping(
//            value = "/cli",
//            method = RequestMethod.POST)
//    public DeferredResult<String> executeCommand(
//            @PathVariable String id,
//            @RequestParam(required = true) String cmd,
//            HttpServletRequest req) throws IOException {
//        return commandService.sendMessage(req, id, cmd);
//    }

}
