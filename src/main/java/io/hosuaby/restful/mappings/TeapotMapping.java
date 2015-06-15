package io.hosuaby.restful.mappings;

/**
 * Mapping with public information about teapot for web clients.
 */
public class TeapotMapping {

    /** Id: valid id */
    private String id;

    /** Name: any String */
    private String name;

    /** Brand: any String */
    private String brand;

    /** Volume: from defined values */
    private float volume;

    /** Current state of the teapot */
    private TeapotState state;

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

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public float getVolume() {
        return volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    public TeapotState getState() {
        return state;
    }

    public void setState(TeapotState state) {
        this.state = state;
    }

}
