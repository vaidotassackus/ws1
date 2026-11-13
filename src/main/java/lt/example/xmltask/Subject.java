package lt.example.xmltask;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;

/** One subject assigned to a student. */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"code", "title", "credits", "passed"})
public class Subject {
    private String code;
    private String title;
    private int credits;
    private boolean passed;

    /** Required by JAXB when reading XML. */
    public Subject() {
    }

    /**
     * Creates a subject with all of its values.
     * @param code subject code
     * @param title subject title
     * @param credits number of credits
     * @param passed whether the subject was passed
     */
    public Subject(String code, String title, int credits, boolean passed) {
        this.code = code;
        this.title = title;
        this.credits = credits;
        this.passed = passed;
    }

    /** Returns the subject code.

     * @return the subject code

     */
    public String getCode() {
        return code;
    }

    /** Sets the subject code.

     * @param code the subject code

     */
    public void setCode(String code) {
        this.code = code;
    }

    /** Returns the subject title.

     * @return the subject title

     */
    public String getTitle() {
        return title;
    }

    /** Sets the subject title.

     * @param title the subject title

     */
    public void setTitle(String title) {
        this.title = title;
    }

    /** Returns the number of credits.

     * @return the number of credits

     */
    public int getCredits() {
        return credits;
    }

    /** Sets the number of credits.

     * @param credits the number of credits

     */
    public void setCredits(int credits) {
        this.credits = credits;
    }

    /** Returns whether the subject was passed.

     * @return whether the subject was passed

     */
    public boolean isPassed() {
        return passed;
    }

    /** Sets whether the subject was passed.

     * @param passed whether the subject was passed

     */
    public void setPassed(boolean passed) {
        this.passed = passed;
    }

    /** Returns a readable summary for console output.

     * @return a readable summary for console output

     */
    @Override
    public String toString() {
        return code + " — " + title + " (" + credits + " credits, passed: " + passed + ")";
    }
}
