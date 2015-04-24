package io.hosuaby.restful.domain;

/**
 * Any device's interface.
 *
 * @author Alexei KLENIN
 */
public abstract class Interface {

    private String id;          // id of the interface
    private String name;        // name of the interface

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

}
