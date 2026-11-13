package lt.example.xmltask;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import org.junit.jupiter.api.Test;

class XmlClientTest {
    @Test
    void receivesBytesUntilServerClosesConnection() throws Exception {
        try (ServerSocket server = new ServerSocket(0)) {
            CompletableFuture<Void> sending = CompletableFuture.runAsync(() -> {
                try (Socket socket = server.accept()) {
                    socket.getOutputStream().write("<student/>".getBytes(StandardCharsets.UTF_8));
                } catch (Exception exception) {
                    throw new RuntimeException(exception);
                }
            });
            byte[] received = new XmlClient().receiveXml("localhost", server.getLocalPort());
            assertEquals("<student/>", new String(received, StandardCharsets.UTF_8));
            sending.join();
        }
    }
}
