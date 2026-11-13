package lt.example.xmltask;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;

class StudentTest {
    @Test
    void storesEveryFieldAndDependentSubjects() {
        Student student = new Student();
        student.setName("Aistė");
        student.setAge(20);
        student.setAverageGrade(9.5f);
        student.setActive(true);
        student.setMiddleInitial('A');
        student.setSubjects(List.of(new Subject("INF101", "Programavimas", 6, true)));

        assertEquals("Aistė", student.getName());
        assertEquals(20, student.getAge());
        assertEquals(9.5f, student.getAverageGrade());
        assertTrue(student.isActive());
        assertEquals('A', student.getMiddleInitial());
        assertEquals(1, student.getSubjects().size());
        assertTrue(student.toString().contains("INF101"));
    }

    @Test
    void xmlBridgePreservesCharacter() {
        Student student = new Student();
        student.setXmlMiddleInitial("J");
        assertEquals('J', student.getMiddleInitial());
        assertEquals("J", student.getXmlMiddleInitial());
    }
}
