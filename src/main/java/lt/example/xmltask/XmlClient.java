package lt.example.xmltask;

import java.io.IOException;
import java.net.Socket;

/** Receives an XML document from a TCP server. */
public class XmlClient {
    /** Creates an XML client. */
    public XmlClient() {
    }

    /**
     * Connects to a server and reads until it closes the connection.
     *
     * @param host server host name
     * @param port server port number
     * @return XML bytes received over the network
     * @throws IOException if the connection or transfer fails
     */
    public byte[] receiveXml(String host, int port) throws IOException {
        try (Socket socket = new Socket(host, port)) {
            return socket.getInputStream().readAllBytes();
        }
    }
}
