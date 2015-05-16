package io.hosuaby.restful.config;

import io.hosuaby.restful.websocket.handlers.ConsoleHandler;
import io.hosuaby.restful.websocket.handlers.NotificationHandler;
import io.hosuaby.restful.websocket.handlers.TeapotHandler;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.PerConnectionWebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry
            .addHandler(notificationHandler(), "/teapots")
            .addHandler(teapotHandler(), "/teapots/register/{teapotId}")
            .addHandler(consoleHandler(), "/teapots/{teapotId}")
            .setAllowedOrigins("*")    // allow all origins
            .addInterceptors(new UriTemplateHandshakeInterceptor());
    }

    /**
     * @return one notification handler bean for all WebSocket connections.
     */
    @Bean
    public WebSocketHandler notificationHandler() {
        return new NotificationHandler();
    }

    /**
     * @return one teapot handler bean per WebSocket connection.
     */
    @Bean
    public WebSocketHandler teapotHandler() {
        return new PerConnectionWebSocketHandler(TeapotHandler.class);
    }

    /**
     * @return one console handler bean per WebSocket connection.
     */
    @Bean
    public WebSocketHandler consoleHandler() {
        return new PerConnectionWebSocketHandler(ConsoleHandler.class);
    }

    /**
     * Handshake interceptor that copies URI template variables to attributes
     * of the WebSocket session. Working together with SimpleUrlHandlerMapping
     * of Spring MVC that copy path variables into attributes of the
     * HttpServletRequest.
     */
    private class UriTemplateHandshakeInterceptor
            implements HandshakeInterceptor {

        @Override
        public boolean beforeHandshake(ServerHttpRequest request,
                ServerHttpResponse response, WebSocketHandler wsHandler,
                Map<String, Object> attributes) throws Exception {

            /* Retrieve original Http request */
            HttpServletRequest origRequest =
                    ((ServletServerHttpRequest) request).getServletRequest();

            /* Retrieve template variables */
            Map<String, String> uriTemplateVars =
                    (Map<String, String>) origRequest
                        .getAttribute(
                            HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

            /* Put template variables into WebSocket session attributes */
            if (uriTemplateVars != null) {
                attributes.putAll(uriTemplateVars);
            }

            return true;
        }

        @Override
        public void afterHandshake(ServerHttpRequest request,
                ServerHttpResponse response, WebSocketHandler wsHandler,
                Exception exception) {}

    }

}
