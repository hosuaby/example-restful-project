package io.hosuaby.restful.simulators;

import io.hosuaby.restful.domain.Teapot;
import io.hosuaby.restful.domain.TeapotMessage;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.websocket.ClientEndpointConfig;
import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Creates simulator of teapot that communicates with server via websocket.
 */
// TODO: get port from environment instead use of PortHolder
public class TeapotSimulator {

    /** EOF character */
    private static final char EOT = 0x004;

    /** Cancel character */
    private static final char CAN = 0x024;

    /** URL for teapot registration */
    private static final String REGISTER_URL = "ws://0.0.0.0:%d/teapots/register/%s";

    /** Pattern to match tokens from message payload */
    private static final Pattern TOKENS_PATTERN = Pattern.compile("\\S+");

    // TODO: Implement custom protocol instead of manual conversion with Jackson
    private static final ObjectMapper mapper = new ObjectMapper();

    /** Teapot file system */
    private TeapotFs fs;

    /**
     * Creates and starts teapot simulator.
     *
     * @param teapot    teapot domain object
     * @param port      port number of the server
     *
     * @throws URISyntaxException
     * @throws DeploymentException
     * @throws IOException
     */
    public TeapotSimulator(Teapot teapot, int port)
            throws URISyntaxException, DeploymentException, IOException {

        /* Get websocket container */
        final WebSocketContainer container = ContainerProvider
                .getWebSocketContainer();

        /* Configuration of teapot client endpoint */
        final ClientEndpointConfig teapotConfig = ClientEndpointConfig.Builder
                .create()
                .build();

        /* Disable websocket timeout */
        container.setDefaultMaxSessionIdleTimeout(0);

        URI uri = new URI(String.format(REGISTER_URL, port, teapot.getId()));

        /* Create websocket client for the teapot */
        container.connectToServer(
                new TeapotSimulatorEndpoint(this), teapotConfig, uri);

        /* Create the file system */
        fs = new TeapotFs();

        /* Create help.txt file */
        fs.cat("help.txt", createHelpFileContent());

        /* Create license file */
        fs.cat("license", createLicenseFileContent());

        /* Create config.json file */
        fs.cat("config.json", createConfigFileContent(teapot));
    }

    /**
     * Executes <code>ls</code> command on the teapot simulator.
     */
    public String[] ls() {
        return fs.ls();
    }

    /**
     * Executes <code>cat</code> command on the teapot simulator.
     *
     * @param filename    filename
     * @return content of the file
     */
    public String[] cat(String filename) {
        return fs.cat(filename);
    }

    /**
     * Executes <code>cat</code> command on the teapot simulator.
     *
     * @param filename    filename
     * @param content     content to write
     */
    public void cat(String filename, String[] content) {
        fs.cat(filename, content);
    }

    /**
     * Executes <code>touch</code> command on the teapot simulator.
     *
     * @param filename    file to create
     */
    public void touch(String filename) {
        fs.touch(filename);
    }

    /**
     * Executes <code>touch</code> command on the teapot simulator.
     *
     * @param oldFilename    old filename
     * @param newFilename    new filename
     */
    public void mv(String oldFilename, String newFilename) {
        fs.mv(oldFilename, newFilename);
    }

    /**
     * Executes <code>touch</code> command on the teapot simulator.
     *
     * @param filename    filename
     */
    public void rm(String filename) {
        fs.rm(filename);
    }

    /**
     * @return content for help.txt file
     */
    private static String[] createHelpFileContent() {
        return new String[] {
                "Teapot simulator. Available commands:",
                "    ls     lists existing files",
                "    cat    prints content to stdout or to file",
                "    touch  creates new empty file",
                "    mv     renames the file",
                "    rm     remove the file"
        };
    }

    /**
     * @return content for license file
     */
    private static String[] createLicenseFileContent() {
        return new String[] {
                "The MIT License (MIT)",
                "",
                "Copyright (c) 2015 Alexei KLENIN",
                "",
                "Permission is hereby granted, free of charge, to any person obtaining a copy",
                "of this software and associated documentation files (the \"Software\"), to deal",
                "in the Software without restriction, including without limitation the rights",
                "to use, copy, modify, merge, publish, distribute, sublicense, and/or sell",
                "copies of the Software, and to permit persons to whom the Software is",
                "furnished to do so, subject to the following conditions:",
                "",
                "The above copyright notice and this permission notice shall be included in all",
                "copies or substantial portions of the Software.",
                "",
                "THE SOFTWARE IS PROVIDED \"AS IS\", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR",
                "IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,",
                "FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE",
                "AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER",
                "LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,",
                "OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE",
                "SOFTWARE."
        };
    }

    /**
     * Creates content of config.json file from teapot domain object.
     *
     * @param teapot    teapot domain object
     * @return content of config.json file
     */
    private static String[] createConfigFileContent(Teapot teapot) {
        return new String[] {
                "{",
                "    \"id\": \""   + teapot.getId()     + "\",",
                "    \"name\": \"" + teapot.getName()   + "\",",
                "    \"brand:\""   + teapot.getBrand()  + "\",",
                "    \"volume:\""  + teapot.getVolume() + "\"",
                "}"
        };
    }

    /**
     * Websocket endpoint of teapot simulator.
     */
    private static class TeapotSimulatorEndpoint extends Endpoint {

        /** Used simulator */
        private TeapotSimulator simulator;

        /**
         * Constructor from simulator object.
         *
         * @param simulator    simulator object
         */
        public TeapotSimulatorEndpoint(TeapotSimulator simulator) {
            this.simulator = simulator;
        }

        /**
         * Attaches message handler on opening of the session.
         */
        @Override
        public void onOpen(Session session, EndpointConfig config) {
            session.addMessageHandler(
                    new TeapotSimulatorMessageHandler(simulator, session));
        }

        /**
         * Prints the reason of closure of the session of the standard output.
         */
        @Override
        public void onClose(Session session, CloseReason closeReason) {
            System.out.println("Session of teapot was closed for reason: "
                    + closeReason.getCloseCode() + " "
                    + closeReason.getReasonPhrase());
        }

    }

    /**
     * Handler for incoming messages.
     */
    private static class TeapotSimulatorMessageHandler
            implements MessageHandler.Whole<String> {

        /** Teapot simulator object */
        private TeapotSimulator simulator;

        /** Websocket session */
        private Session session;

        /**
         * Constructor from simulator and websocket session.
         *
         * @param simulator    simulator object
         * @param session      websocket object
         */
        public TeapotSimulatorMessageHandler(TeapotSimulator simulator,
                Session session) {
            this.simulator = simulator;
            this.session = session;
        }

        /**
         * Handler for incoming messages.
         */
        @Override
        public void onMessage(String message) {
            TeapotMessage msg;
            try {
                msg = mapper.readValue(message, TeapotMessage.class);
                String clientId = msg.getClientId();
                String payload = msg.getPayload();

                /* Cut payload on tokens */
                Matcher matcher = TOKENS_PATTERN.matcher(payload);
                List<String> args = new ArrayList<>();

                while (matcher.find()) {
                    args.add(matcher.group());
                }

                String cmd = args.remove(0);    // first arg is a command

                switch (cmd) {
                case "ls":
                    sendAnswer(session, clientId, simulator.ls());
                    break;
                case "cat":
                    switch (args.size()) {
                    case 1:
                        sendAnswer(session, clientId,
                                simulator.cat(args.get(0)));
                        break;
                    case 2:
                        simulator.cat(args.get(0), args.get(1).split("\\n"));
                        break;
                    }
                    break;
                case "touch":
                    simulator.touch(args.get(0));
                    break;
                case "mv":
                    simulator.mv(args.get(0), args.get(1));
                    break;
                case "rm":
                    simulator.rm(args.get(0));
                    break;
                default:
                    sendError(session, clientId, "Command " + cmd
                            + " is not supported!");
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * Sends the answer to the client via websocket session and adds EOT
         * character at the end to indicate the end of transmission.
         *
         * @param session     websocket session
         * @param clientId    client id
         * @param answer      text data to send
         */
        private void sendAnswer(Session session, String clientId,
                String[] answer) {
            try {

                /* Send lines as separate messages */
                int length = answer.length;
                for (int i = 0; i < length - 1; i++) {
                    String line = answer[i];
                    TeapotMessage message = new TeapotMessage(
                            clientId, line);

                    session.getBasicRemote().sendText(
                            mapper.writeValueAsString(message));
                }

                /* Send last message */
                TeapotMessage message = new TeapotMessage(
                        clientId, answer[length - 1] + EOT);
                session.getBasicRemote().sendText(
                        mapper.writeValueAsString(message));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * Sends the error message to the client via websocket session and adds
         * CAN character at the end to indicate the end of transmission with
         * error.
         *
         * @param session     websocket session
         * @param clientId    client id
         * @param msg         error message
         */
        private void sendError(Session session, String clientId, String msg) {
            TeapotMessage message = new TeapotMessage(clientId, msg + CAN);
            try {
                session.getBasicRemote().sendText(
                        mapper.writeValueAsString(message));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
