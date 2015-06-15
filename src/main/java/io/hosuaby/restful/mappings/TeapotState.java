package io.hosuaby.restful.mappings;

/**
 * Realtime state of the teapot.
 */
public enum TeapotState {

    /** Teapot connected to the server and waiting for commands */
    IDLE,

    /** Teapot is not connected to the server */
    UNAVAILABLE

}
