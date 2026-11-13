package lt.example.xmltask;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class SubjectTest {
    @Test
    void storesAndPrintsFields() {
        Subject subject = new Subject();
        subject.setCode("WEB101");
        subject.setTitle("Žiniatinklis");
        subject.setCredits(5);
        subject.setPassed(false);

        assertEquals("WEB101", subject.getCode());
        assertEquals("Žiniatinklis", subject.getTitle());
        assertEquals(5, subject.getCredits());
        assertFalse(subject.isPassed());
        assertTrue(subject.toString().contains("WEB101"));

        Subject other = new Subject("MAT101", "Matematika", 6, true);
        assertTrue(other.isPassed());
    }
}
