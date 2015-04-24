package io.hosuaby.restful.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * Any network device.
 *
 * @author Alexei KLENIN
 */
public abstract class Device {

    private String id;                      // device id
    private String name;                    // device name
    private String description;             // device description
    private Set<Interface> interfaces = new HashSet<>();    // interfaces of this device

    public Device() {
        super();
    }

    public Device(String id, String name, String description) {
        super();
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Interface> getInterfaces() {
        return interfaces;
    }

    public void setInterfaces(Set<Interface> interfaces) {
        this.interfaces = interfaces;
    }

}
