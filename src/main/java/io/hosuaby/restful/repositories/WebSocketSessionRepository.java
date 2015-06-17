package io.hosuaby.restful.repositories;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.web.socket.WebSocketSession;

/**
 * Repository for {@link WebSocketSession} backed by {@link Map}. Repository is
 * not thread safe! Thread synchronization must be made in service layer.
 */
public class WebSocketSessionRepository {

    /**
     * Sessions store.
     */
    private Map<String, WebSocketSession> sessions;

    /**
     * Creates an instance backed by a {@link ConcurrentHashMap}
     */
    public WebSocketSessionRepository() {
        sessions = new HashMap<>();     // not thread safe HashMap
    }

    /**
     * Saves the session. If session was already registered method does nothing.
     *
     * @param session    web socket session
     */
    public void save(WebSocketSession session) {
        sessions.put(session.getId(), session);
    }

    /**
     * Returns all websocket sessions from this repository.
     *
     * @return collection of websocket sessions
     */
    public Collection<WebSocketSession> getSessions() {
        return sessions.values();
    }

    /**
     * Returns the session by it's id.
     *
     * @param id    session id
     *
     * @return websocket session, if no session was found returns null.
     */
    public WebSocketSession getSession(String id) {
        return sessions.get(id);
    }

    /**
     * Deletes the session with defined id. If not session with this id was
     * defined, method does nothing.
     *
     * @param id    session id
     */
    public void delete(String id) {
        sessions.remove(id);
    }

    /**
     * Deletes the session. If defined session was not found, method does
     * nothing.
     *
     * @param session    websocket session
     */
    public void delete(WebSocketSession session) {
        sessions.remove(session.getId());
    }

}
