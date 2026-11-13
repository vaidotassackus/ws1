package lt.example.xmltask;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.xml.bind.JAXBException;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

class XmlTransformerTest {
    @Test
    void readsAndWritesTenSubjectsWithEveryFieldType() throws Exception {
        XmlTransformer transformer = new XmlTransformer();
        try (InputStream xml = getClass().getResourceAsStream("/student.xml")) {
            Student student = transformer.transformToPOJO(xml);
            assertEquals("Jonas Jonaitis", student.getName());
            assertEquals(21, student.getAge());
            assertEquals(8.7f, student.getAverageGrade());
            assertTrue(student.isActive());
            assertEquals('J', student.getMiddleInitial());
            assertEquals(10, student.getSubjects().size());
            assertEquals("MAT101", student.getSubjects().get(0).getCode());

            String generatedXml = transformer.transformToXML(student);
            assertTrue(generatedXml.contains("<middleInitial>J</middleInitial>"));
            Student roundTrip = transformer.transformToPOJO(new ByteArrayInputStream(
                    generatedXml.getBytes(StandardCharsets.UTF_8)));
            assertEquals(student.getSubjects().size(), roundTrip.getSubjects().size());
            assertEquals('J', roundTrip.getMiddleInitial());
        }
    }

    @Test
    void rejectsDocumentWithFewerThanTenSubjects() throws Exception {
        String invalid = """
                <student><name>A</name><age>20</age><averageGrade>8.0</averageGrade>
                <active>true</active><middleInitial>A</middleInitial><subjects>
                <subject><code>X</code><title>X</title><credits>5</credits><passed>true</passed></subject>
                </subjects></student>
                """;
        XmlTransformer transformer = new XmlTransformer();
        assertThrows(JAXBException.class, () -> transformer.transformToPOJO(
                new ByteArrayInputStream(invalid.getBytes(StandardCharsets.UTF_8))));
    }
}
