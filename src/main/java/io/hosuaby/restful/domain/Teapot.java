package io.hosuaby.restful.domain;

import java.net.Inet4Address;

/**
 * Just a teapot.
 */
public class Teapot {

    /** 0.3 L */
    public static final float L0_3 = (float)  0.3;

    /** 0.5 L */
    public static final float L0_5 = (float)  0.5;

    /** 1 L */
    public static final float L1   = (float)  1.0;

    /** 1.5 L */
    public static final float L1_5 = (float)  1.5;

    /** 3 L */
    public static final float L3   = (float)  3.0;

    /** 5 L */
    public static final float L5   = (float)  5.0;

    /** 8 L */
    public static final float L8   = (float)  8.0;

    /** 10 L */
    public static final float L10  = (float) 10.0;

    /** Id: valid id */
    private String id;

    /** Name: any String */
    private String name;

    /** Brand: any String */
    private String brand;

    /** Volume: from defined values */
    private float volume;

    /** Current IP address. Must be hidden from the web user */
    private Inet4Address ip;

    public Teapot() {
        super();
    }

    public Teapot(String id, String name, String brand, float volume) {
        super();
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.volume = volume;
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

    public Inet4Address getIp() {
        return ip;
    }

    public void setIp(Inet4Address ip) {
        this.ip = ip;
    }

}
