package io.hosuaby.restful.websocket.handlers;

import io.hosuaby.restful.domain.Teapot;
import io.hosuaby.restful.domain.TeapotMessage;
import io.hosuaby.restful.services.TeapotCrudService;
import io.hosuaby.restful.services.TeapotCommandService;
import io.hosuaby.restful.services.exceptions.teapots.TeapotAlreadyExistsException;
import io.hosuaby.restful.services.exceptions.teapots.TeapotNotExistsException;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Handler of incomming websocket connections from teapots.
 */
public class TeapotHandler extends TextWebSocketHandler {

    /** End-of-transmission character */
    private static final char EOT = 0x004;

    /** Cancel character */
    private static final char CAN = 0x024;

    /** Teapot CRUD service */
    @Autowired
    private TeapotCrudService crud;

    /** Teapot command service */
    @Autowired
    private TeapotCommandService commandService;

    /** Jackson object mapper */
    @Autowired
    private ObjectMapper jacksonMapper;

    /** Randomizer */
    @Autowired
    private Random randomizer;

    /**
     * Assign IP address to teapot and registers it into command service.
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session)
            throws IOException {
        String teapotId = (String) session.getAttributes().get("teapotId");

        /* Registers the teapot first */
        commandService.register(teapotId, session);

        /* Find the teapot by id */
        // TODO: move this check into handshake handler
        try {
            Teapot teapot = crud.find(teapotId);

            /* Assign IP adress to the teapot */
            Inet4Address ip = (Inet4Address) InetAddress.getByAddress(new byte[] {
                    (byte) 192, (byte) 168, (byte) 13, (byte) randomizer.nextInt(255)
            });

            teapot.setIp(ip);
            crud.update(teapotId, teapot);
        } catch (TeapotNotExistsException | TeapotAlreadyExistsException e) {

            /* Close websocket session */
            session.close(CloseStatus.NORMAL.withReason(e.getMessage()));

            e.printStackTrace();
        }
    }

    /**
     * Relays the message from the teapot to the client.
     */
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message)
            throws JsonParseException, JsonMappingException, IOException {
        // TODO: replace Jackson by custom protocole
        TeapotMessage msg = jacksonMapper.readValue(
                message.getPayload(), TeapotMessage.class);

        String clientId = msg.getClientId();
        String payload  = msg.getPayload();

        char lastCharacter = payload.charAt(payload.length() - 1);

        /* If last character is EOT */
        boolean eot = lastCharacter == EOT;

        /* If first chararcter is CAN */
        boolean can = lastCharacter == CAN;

        /* Remove last character from payload if it is a control character */
        if (eot || can) {
            payload = payload.substring(0, payload.length() - 1);
        }

        /* Canceled by teapot */
        if (can) {
            commandService.submitError(clientId, payload);
            return;
        }

        commandService.submitResponse(msg.getClientId(), payload, eot);
    }

    /**
     * Unregisters disconnected teapot.
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        String teapotId = (String) session.getAttributes().get("teapotId");

        /* Unregister teapot first */
        commandService.unregister(session);

        /* Set teapot's IP address to null */
        try {
            Teapot teapot = crud.find(teapotId);
            teapot.setIp(null);
            crud.update(teapotId, teapot);
        } catch (TeapotNotExistsException | TeapotAlreadyExistsException e) {
            e.printStackTrace();
        }
    }

}
