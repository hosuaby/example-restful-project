package io.hosuaby.restful.controllers;

import io.hosuaby.restful.domain.Device;
import io.hosuaby.restful.services.DeviceService;
import io.hosuaby.restful.services.exceptions.devices.DeviceAlreadyExistsException;
import io.hosuaby.restful.services.exceptions.devices.DeviceNotFoundException;

import java.io.IOException;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * API for management of devices.
 *
 * @author Alexei KLENIN
 */
@RestController
@RequestMapping("/devices")
// TODO: remove exception handling from controller and create @ExceptionHandler
public class DeviceController {

    private static final String ERR_DEVICE_NOT_FOUND = "Device with id %s was not found!";
    private static final String ERR_DEVICE_ALREADY_EXISTS = "Device with id %s already exists!";

    /**
     * Device service.
     */
    @Autowired
    private DeviceService deviceService;

    /**
     * Returns all devices.
     *
     * @return set of device objects
     */
    @RequestMapping(
            value    = "/",
            method   = RequestMethod.GET,
            produces = "application/json")
    @ResponseBody
    public Set<Device> getAllDevices() {
        return deviceService.getAllDevices();
    }

    /**
     * Returns the device found by id.
     *
     * @param id              device's id
     * @param httpResponse    HTTP response object
     *
     * @return device object
     *
     * @throws IOException
     */
    @RequestMapping(
            value    = "/{id}",
            method   = RequestMethod.GET,
            produces = "application/json")
    public Device findDeviceById(
            @PathVariable String id,
            HttpServletResponse httpResponse) throws IOException {
        Device device = deviceService.findDeviceById(id);

        if (device == null) {
            httpResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
            httpResponse.getWriter().printf(ERR_DEVICE_NOT_FOUND, id);
        }

        return device;
    }

    /**
     * Adds a new device.
     *
     * @param device          device object
     * @param httpResponse    HTTP response object
     *
     * @throws IOException
     */
    @RequestMapping(
            value  = "/",
            method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void addDevice(
            @RequestBody Device device,
            HttpServletResponse httpResponse) throws IOException {
        try {
            deviceService.addDevice(device);
        } catch (DeviceAlreadyExistsException e) {
            httpResponse.setStatus(HttpServletResponse.SC_CONFLICT);
            httpResponse.getWriter().printf(
                    ERR_DEVICE_ALREADY_EXISTS, device.getId());
        }
    }

    /**
     * Updates existing device.
     *
     * @param id              device id
     * @param device          device object
     * @param httpResponse    HTTP response object
     *
     * @throws IOException
     */
    @RequestMapping(
            value  = "/{id}",
            method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateDevice(
            @PathVariable String id,
            @RequestBody Device device,
            HttpServletResponse httpResponse) throws IOException {
        try {
            deviceService.updateDevice(id, device);
        } catch (DeviceAlreadyExistsException e) {
            httpResponse.setStatus(HttpServletResponse.SC_CONFLICT);
            httpResponse.getWriter().printf(
                    ERR_DEVICE_ALREADY_EXISTS, device.getId());
        } catch (DeviceNotFoundException e) {
            httpResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
            httpResponse.getWriter().printf(ERR_DEVICE_NOT_FOUND, id);
        }
    }

    /**
     * Removes existing device.
     *
     * @param id              device id
     * @param httpResponse    HTTP response object
     *
     * @throws IOException
     */
    @RequestMapping(
            value  = "/{id}",
            method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDevice(
            @PathVariable String id,
            HttpServletResponse httpResponse) throws IOException {
        try {
            deviceService.deleteDeviceById(id);
        } catch (DeviceNotFoundException e) {
            httpResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
            httpResponse.getWriter().printf(ERR_DEVICE_NOT_FOUND, id);
        }
    }

}
