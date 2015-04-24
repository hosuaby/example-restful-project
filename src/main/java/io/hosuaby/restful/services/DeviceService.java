package io.hosuaby.restful.services;

import io.hosuaby.restful.domain.Device;
import io.hosuaby.restful.domain.devices.Router;
import io.hosuaby.restful.domain.devices.Switch;
import io.hosuaby.restful.services.exceptions.devices.DeviceAlreadyExistsException;
import io.hosuaby.restful.services.exceptions.devices.DeviceNotFoundException;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

/**
 * Provides methods to manipulate devices.
 *
 * @author Alexei KLENIN
 */
@Service
public class DeviceService {

    /**
     * In memory device store.
     */
    private final Set<Device> devices = new HashSet<>();

    /**
     * Reset the collection of devices.
     */
    @PostConstruct
    public void resetDevices() {
        devices.clear();        // clear set of devices

        /* Add some routers */
        devices.add(new Router("RTR1", "Luke", "First router"));
        devices.add(new Router("RTR2", "Yoda", "Second router"));

        /* Add some switches */
        devices.add(new Switch("SW1", "Dart Waider", "I am your father!"));
        devices.add(new Switch("SW2", "Jaba", "I am awesome"));
        devices.add(new Switch("SW3", "Grievous", "&#\\^"));
    }

    /**
     * Returns all devices.
     *
     * @return all devices
     */
    public Set<Device> getAllDevices() {
        return devices;
    }

    /**
     * Returns the device found by it's id. If no device found, method returns
     * null.
     *
     * @param id    device id
     *
     * @return device object or null
     */
    public Device findDeviceById(String id) {
        for (Device device : devices) {
            if (device.getId().equals(id)) {
                return device;
            }
        }
        return null;
    }

    /**
     * Adds the device into collection.
     *
     * @param device    device object to add
     *
     * @return added device object
     *
     * @throws DeviceAlreadyExistsException
     *      thrown when try to add device with id that already exists
     */
    public Device addDevice(Device device) throws DeviceAlreadyExistsException {
        for (Device dev : devices) {
            if (dev.getId().equals(device.getId())) {
                throw new DeviceAlreadyExistsException();
            }
        }
        devices.add(device);
        return device;
    }

    /**
     * Updates an device from collection and returns the device before update.
     *
     * @param id        device's id
     * @param device    device object
     *
     * @return device object before update
     *
     * @throws DeviceAlreadyExistsException
     *      thrown when new device's id enter in collision
     * @throws DeviceNotFoundException
     *      thrown when updates device is not found
     */
    // TODO: optimize this method
    public Device updateDevice(String id, Device device) throws
            DeviceAlreadyExistsException, DeviceNotFoundException {

        /* If device changed id check that there is no collision */
        if (!id.equals(device.getId())) {
            if (findDeviceById(device.getId()) != null) {
                throw new DeviceAlreadyExistsException();
            }
        }

        /* Find device */
        Device dev = findDeviceById(id);
        if (dev != null) {
            devices.remove(dev);
            devices.add(device);
        } else {
            throw new DeviceNotFoundException();
        }

        return null;
    }

    /**
     * Deletes an device object from collection and returns deleted device.
     *
     * @param id    device's id
     *
     * @return deleted device object
     *
     * @throws DeviceNotFoundException
     *      thrown when updates device is not found
     */
    public Device deleteDeviceById(String id) throws DeviceNotFoundException {

        /* Find device */
        Device device = findDeviceById(id);

        /* If device not found throw an exception */
        if (device == null) {
            throw new DeviceNotFoundException();
        }

        /* Remove device object from collection */
        devices.remove(device);

        return device;
    }

}
