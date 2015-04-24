package io.hosuaby.restful.domain.devices;

import io.hosuaby.restful.domain.Device;

/**
 * Class of switch device.
 *
 * @author Alexei KLENIN
 */
public class Switch extends Device {

    public Switch() {
        super();
    }

    public Switch(String id, String name, String description) {
        super(id, name, description);
    }

}
