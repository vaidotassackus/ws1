package lt.example.xmltask;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

/** Root POJO containing student details and dependent subjects. */
@XmlRootElement(name = "student")
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(propOrder = {"name", "age", "averageGrade", "active", "xmlMiddleInitial", "subjects"})
public class Student {
    @XmlElement
    private String name;
    @XmlElement
    private int age;
    @XmlElement
    private float averageGrade;
    @XmlElement
    private boolean active;
    private char middleInitial;

    @XmlElementWrapper(name = "subjects")
    @XmlElement(name = "subject")
    private List<Subject> subjects = new ArrayList<>();

    /** Required by JAXB when reading XML. */
    public Student() {
    }

    /** Returns the student's name.

     * @return the student's name

     */
    public String getName() {
        return name;
    }

    /** Sets the student's name.

     * @param name the student's name

     */
    public void setName(String name) {
        this.name = name;
    }

    /** Returns the student's age.

     * @return the student's age

     */
    public int getAge() {
        return age;
    }

    /** Sets the student's age.

     * @param age the student's age

     */
    public void setAge(int age) {
        this.age = age;
    }

    /** Returns the average grade.

     * @return the average grade

     */
    public float getAverageGrade() {
        return averageGrade;
    }

    /** Sets the average grade.

     * @param averageGrade the average grade

     */
    public void setAverageGrade(float averageGrade) {
        this.averageGrade = averageGrade;
    }

    /** Returns whether the student is active.

     * @return whether the student is active

     */
    public boolean isActive() {
        return active;
    }

    /** Sets whether the student is active.

     * @param active whether the student is active

     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /** Returns the student's middle initial.

     * @return the student's middle initial

     */
    public char getMiddleInitial() {
        return middleInitial;
    }

    /** Sets the student's middle initial.

     * @param middleInitial the student's middle initial

     */
    public void setMiddleInitial(char middleInitial) {
        this.middleInitial = middleInitial;
    }

    /** Returns the Java character as an XML string for JAXB.

     * @return the Java character as an XML string for JAXB

     */
    @XmlElement(name = "middleInitial")
    public String getXmlMiddleInitial() {
        return String.valueOf(middleInitial);
    }

    /** Sets the one-character XML string.

     * @param value the one-character XML string

     */
    public void setXmlMiddleInitial(String value) {
        if (value == null || value.length() != 1) {
            throw new IllegalArgumentException("Middle initial must contain one character");
        }
        middleInitial = value.charAt(0);
    }

    /** Returns the dependent subjects.

     * @return the dependent subjects

     */
    public List<Subject> getSubjects() {
        return subjects;
    }

    /** Sets the dependent subjects.

     * @param subjects the dependent subjects

     */
    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    /** Returns all values in a readable form for console output.

     * @return all values in a readable form for console output

     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("Student: ").append(name)
                .append("\nAge: ").append(age)
                .append("\nAverage grade: ").append(averageGrade)
                .append("\nActive: ").append(active)
                .append("\nMiddle initial: ").append(middleInitial)
                .append("\nSubjects (").append(subjects.size()).append("):");
        for (Subject subject : subjects) {
            result.append("\n  ").append(subject);
        }
        return result.toString();
    }

}
