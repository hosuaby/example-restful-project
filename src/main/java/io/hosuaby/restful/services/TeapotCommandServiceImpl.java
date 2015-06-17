package io.hosuaby.restful.services;

import io.hosuaby.restful.domain.TeapotMessage;
import io.hosuaby.restful.repositories.WebSocketSessionRepository;
import io.hosuaby.restful.services.exceptions.teapots.TeapotInternalErrorException;
import io.hosuaby.restful.services.exceptions.teapots.TeapotNotConnectedException;

import java.io.IOException;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Implementation of the {@link TeapotCommandService}.
 */
@Service
// TODO: implement appropriate thread synchronization
public class TeapotCommandServiceImpl implements TeapotCommandService {

    /**
     * Pattern for request id. Id must start with sequence "req-".
     */
    private static final Pattern REQUEST_ID_PATTERN = Pattern.compile("^req-.*");

    /** Repository for websocket sessions of teapots */
    @Autowired
    @Qualifier("teapotSessionsRepository")
    private WebSocketSessionRepository sessionRepository;

    /** Jackson object mapper */
    @Autowired
    private ObjectMapper jacksonMapper;

    /** Map of session ids by teapot ids */
    private Map<String, String> mapTeapotIdSessionId;

    /** Map of teapot ids by session ids */
    private Map<String, String> mapSessionIdTeapotId;

    /** Deferred results for command executions in Ajax mode */
    private Map<String, DeferredResult<String>> results;

    /**
     * Partial results returned by teapots during execution of the command.
     * Used to buffer content of multiple subsequent messages before EOT
     * character is received and DeferredResult object associated with request
     * can be fulfilled.
     * Partial results are mapped by request id.
     */
    private Map<String, String> partialResults;

    /**
     * Constructor.
     */
    public TeapotCommandServiceImpl() {
        mapTeapotIdSessionId = new HashMap<>();
        mapSessionIdTeapotId = new HashMap<>();
        results = new HashMap<>();
        partialResults = new HashMap<>();
    }

    /** {@inheritDoc} */
    @Override
    public synchronized void register(String teapotId,
            WebSocketSession session) {
        String sessionId = session.getId();

        sessionRepository.save(session);

        mapTeapotIdSessionId.put(teapotId, sessionId);
        mapSessionIdTeapotId.put(sessionId, teapotId);
    }

    /** {@inheritDoc} */
    @Override
    public synchronized void unregister(WebSocketSession teapotSession) {
        String sessionId = teapotSession.getId();
        String teapotId = mapSessionIdTeapotId.get(sessionId);

        sessionRepository.delete(teapotSession);

        mapTeapotIdSessionId.remove(teapotId);
        mapSessionIdTeapotId.remove(sessionId);
    }

    /** {@inheritDoc} */
    @Override
    public void shutdown(String teapotId) throws IOException,
            TeapotNotConnectedException {
        if (mapTeapotIdSessionId.containsKey(teapotId)) {
            sessionRepository
                .getSession(mapTeapotIdSessionId.get(teapotId))
                .close();
        } else {
            throw new TeapotNotConnectedException(teapotId);
        }
    }

    /** {@inheritDoc} */
    @Override
    public synchronized String[] getRegisteredTeapotsIds() {
        return (String[]) mapTeapotIdSessionId.keySet().toArray();
    }

    /** {@inheritDoc} */
    @Override
    public synchronized boolean isTeapotConnected(String teapotId) {
        return mapTeapotIdSessionId.containsKey(teapotId);
    }

    /** {@inheritDoc} */
    @Override
    public DeferredResult<String> sendMessage(HttpServletRequest req,
            String teapotId, String msg) {

        /* Create unique request id */
        String requestId = new StringBuilder("req-")
            .append(req.getSession().getId())   // HTTP session id
            .append('-')
            .append(new GregorianCalendar().getTimeInMillis())  // timestamp
            .toString();

        /* Create message object */
        TeapotMessage message = new TeapotMessage(requestId, msg);

        /* Get teapot websocket session */
        WebSocketSession teapotSession = sessionRepository.getSession(
                mapTeapotIdSessionId.get(teapotId));

        DeferredResult<String> result = new DeferredResult<>();

        if (teapotSession != null) {
            try {
                String msgJson = jacksonMapper.writeValueAsString(message);
                teapotSession.sendMessage(new TextMessage(msgJson));
                results.put(requestId, result);
                partialResults.put(requestId, "");
            } catch (IOException e) {

                /* Reject deferred result */
                result.setErrorResult(e);

                e.printStackTrace();
            }
        } else {

            /* Reject deferred result */
            result.setErrorResult(new TeapotNotConnectedException(teapotId));
        }

        return result;
    }

    /** {@inheritDoc} */
    @Override
    public void submitResponse(String clientId, String msg) {
        if (REQUEST_ID_PATTERN.matcher(clientId).matches()) {

            /* This is request id */

            String partial = Optional
                    .ofNullable(partialResults.get(clientId))
                    .map(s -> s + "\n")
                    .orElse("") + msg;

            partialResults.remove(clientId);

            results.computeIfPresent(clientId, (id, result) -> {
                result.setResult(partial);
                return null;
            });

        } else {

            /* This is websocket session id */
            // TODO: code this behaiviour

        }
    }

    /** {@inheritDoc} */
    @Override
    public void submitResponse(String clientId, String msg, boolean isLast) {
        if (REQUEST_ID_PATTERN.matcher(clientId).matches()) {

            /* This is request id */

            String partial = Optional
                    .ofNullable(partialResults.get(clientId))
                    .map(s -> s + "\n")
                    .orElse("") + msg;

            if (isLast) {
                results.computeIfPresent(clientId, (id, result) -> {
                    result.setResult(partial);
                    return null;
                });
            } else {
                partialResults.put(clientId, partial);
            }

        } else {

            /* This is websocket session id */
            // TODO: code this behaiviour

        }
    }

    /** {@inheritDoc} */
    @Override
    public void submitError(String clientId, String error) {
        if (REQUEST_ID_PATTERN.matcher(clientId).matches()) {

            /* This is request id */

            // TODO: use Optional of Java8 instead
            String partial = partialResults.get(clientId);
            if (partial == null) {
                partial = "";
            }
            partial += (partial.isEmpty() ? "" : "\n") + error;

            partialResults.remove(clientId);

            // TODO: use Optional maybe
            if (results.containsKey(clientId)) {
                results.get(clientId).setErrorResult(
                        new TeapotInternalErrorException(partial));
                results.remove(clientId);
            }

        } else {

            /* This is websocket session id */
            // TODO: code this behaiviour

        }
    }

}
