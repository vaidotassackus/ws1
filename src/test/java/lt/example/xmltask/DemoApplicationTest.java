package lt.example.xmltask;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

class DemoApplicationTest {
    @Test
    void runsCompleteNetworkAndXmlDemonstration() throws Exception {
        PrintStream original = System.out;
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        try (PrintStream captured = new PrintStream(bytes, true, StandardCharsets.UTF_8)) {
            System.setOut(captured);
            DemoApplication.main(new String[0]);
        } finally {
            System.setOut(original);
        }
        String output = bytes.toString(StandardCharsets.UTF_8);
        assertTrue(output.contains("XML received and validated against XSD."));
        assertTrue(output.contains("Subjects (10):"));
        assertTrue(output.contains("<middleInitial>J</middleInitial>"));
    }
}
