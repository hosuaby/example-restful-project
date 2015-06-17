package io.hosuaby.restful.services;

import io.hosuaby.restful.controllers.TeapotCommandController;
import io.hosuaby.restful.services.exceptions.teapots.TeapotNotConnectedException;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.socket.WebSocketSession;

/**
 * Service that provides registration of teapots and communication with them via
 * web socket sessions.
 */
public interface TeapotCommandService {

    /**
     * Registers a newly connected teapot with associated websocket connection.
     *
     * @param teapotId    teapot id
     * @param session     teapot websocket session
     */
    void register(String teapotId, WebSocketSession session);

    /**
     * Unregisters disconnected teapot. This method don't close teapot session.
     * Teapot session must be closed prior to call of this method. Teapot must
     * be registered prior to call of this method.
     *
     * @param teapotSession    teapot websocket session
     */
    void unregister(WebSocketSession teapotSession);

    /**
     * Shutdown the connected teapot. This method is called by
     * {@link TeapotCommandController}.
     *
     * @param teapotId    teapot id.
     *
     * @throws IOException
     *      failed to close teapot session
     * @throws TeapotNotConnectedException
     *      teapot with defined id is not connected
     */
    void shutdown(String teapotId) throws IOException,
            TeapotNotConnectedException;

    /**
     * @return ids of registered teapots.
     */
    String[] getRegisteredTeapotsIds();

    /**
     * Returns true if teapot with defined id is connected, and false if teapot
     * is not connected.
     *
     * @param teapotId    teapot id
     *
     * @return true if teapot is connected, false if not
     */
    boolean isTeapotConnected(String teapotId);

    /**
     * Sends the message to the teapot from Ajax client. This method returns
     * {@link DeferredResult} that will be fulfilled once teapot returns the
     * answer. If any error occurs during processing deferred object is
     * rejected with appropriate error code and message.
     * For chaque Ajax request method generates unique clientId that will be
     * transmitted to teapot.
     *
     * @param req         ajax http request
     * @param teapotId    teapot id
     * @param msg         message to send
     *
     * @return deferred result
     */
    DeferredResult<String> sendMessage(HttpServletRequest req, String teapotId,
            String msg);

    /**
     * Submits response from the teapot to the client. Attribute
     * <code>clientId</code> can reference unique request in case of Ajax
     * request or websocket session if client is connected via websocket.
     * If command was made by Ajax request, associated DeferredResult
     * is fulfilled.
     *
     * @param clientId    request or websocket session id
     * @param msg         message to send
     */
    void submitResponse(String clientId, String msg);

    /**
     * Submits response from the teapot to the client. Attribute
     * <code>clientId</code> can reference unique request in case of Ajax
     * request or websocket session if client is connected via websocket.
     * In the case of Ajax request, if <code>isLast</code> is set to
     * <code>true</code> the message is appended to the buffer instead of
     * immediate fulfill of the <code>DeferredResult</code> object.
     *
     * @param clientId    request or websocket session id
     * @param msg         message to send
     * @param isLast      flag of the last message of the response
     */
    void submitResponse(String clientId, String msg, boolean isLast);

    /**
     * Sends error message back to client.
     *
     * @param clientId    request or websocket session id
     * @param error       error message
     */
    void submitError(String clientId, String error);

}
