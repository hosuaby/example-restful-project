package io.hosuaby.restful.domain;


/**
 * Message object for exchange of commands and results between server and
 * teapot. Contains client id and payload. Payload can be the command when
 * message sent from server to teapot or result when sent from teapot to server.
 */
public class TeapotMessage {

    /** Client id */
    private String clientId;

    /** Message payload */
    private String payload;

    public TeapotMessage() {
        super();
    }

    public TeapotMessage(String clientId, String payload) {
        super();
        this.clientId = clientId;
        this.payload = payload;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

}
