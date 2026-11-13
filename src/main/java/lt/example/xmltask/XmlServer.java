package lt.example.xmltask;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/** Sends the sample XML to one local TCP client. */
public class XmlServer implements AutoCloseable {
    private final ServerSocket serverSocket;

    /**
     * Binds a port; zero asks the operating system to choose a free port.
     * @param port requested port
     * @throws IOException if the port cannot be bound
     */
    public XmlServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    /** Returns the actual listening port.

     * @return the actual listening port

     */
    public int getPort() {
        return serverSocket.getLocalPort();
    }

    /**
     * Waits for one client, sends the XML resource, then returns.
     * @throws IOException if the transfer fails
     */
    public void sendOnce() throws IOException {
        try (Socket client = serverSocket.accept();
                InputStream xml = XmlServer.class.getResourceAsStream("/student.xml")) {
            if (xml == null) {
                throw new IOException("student.xml resource was not found");
            }
            xml.transferTo(client.getOutputStream());
        }
    }

    /**
     * Closes the listening socket.
     * @throws IOException if the socket cannot be closed
     */
    @Override
    public void close() throws IOException {
        serverSocket.close();
    }
}
