package io.hosuaby.restful;

import org.springframework.boot.context.embedded.EmbeddedServletContainerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * Bean that provides the port number of this application at runtime.
 */
@Component
public class PortHolder implements ApplicationListener<EmbeddedServletContainerInitializedEvent>  {

    private int port;

    @Override
    public void onApplicationEvent(
            EmbeddedServletContainerInitializedEvent event) {
        port = event.getEmbeddedServletContainer().getPort();
    }

    public int getPort() {
        return port;
    }

}
