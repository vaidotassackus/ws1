package lt.example.xmltask;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import org.junit.jupiter.api.Test;

class XmlServerTest {
    @Test
    void sendsSampleXmlOverTcp() throws Exception {
        try (XmlServer server = new XmlServer(0)) {
            CompletableFuture<Void> sending = CompletableFuture.runAsync(() -> {
                try {
                    server.sendOnce();
                } catch (Exception exception) {
                    throw new RuntimeException(exception);
                }
            });
            try (Socket socket = new Socket("localhost", server.getPort())) {
                String xml = new String(socket.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
                assertTrue(xml.contains("<student>"));
                assertTrue(xml.contains("<code>PHY101</code>"));
            }
            sending.join();
        }
    }
}
