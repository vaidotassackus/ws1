package lt.example.xmltask;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.concurrent.CompletableFuture;

/** Runs the server, client and both JAXB transformations in one command. */
public final class DemoApplication {
    private DemoApplication() {
    }

    /**
     * Starts the complete demonstration on localhost.
     * @param args unused command-line arguments
     * @throws Exception if the network transfer or XML transformation fails
     */
    public static void main(String[] args) throws Exception {
        try (XmlServer server = new XmlServer(0)) {
            CompletableFuture<Void> sending = CompletableFuture.runAsync(() -> {
                try {
                    server.sendOnce();
                } catch (IOException exception) {
                    throw new UncheckedIOException(exception);
                }
            });

            byte[] receivedXml = new XmlClient().receiveXml("localhost", server.getPort());
            sending.join();

            XmlTransformer transformer = new XmlTransformer();
            Student student = transformer.transformToPOJO(new ByteArrayInputStream(receivedXml));
            System.out.println("XML received and validated against XSD.");
            System.out.println(student);
            System.out.println("\nXML generated from the POJO:");
            System.out.println(transformer.transformToXML(student));
        }
    }
}
