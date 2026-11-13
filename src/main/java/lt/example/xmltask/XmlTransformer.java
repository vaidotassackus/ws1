package lt.example.xmltask;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import java.io.InputStream;
import java.io.StringWriter;
import javax.xml.XMLConstants;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.xml.sax.SAXException;

/** Encapsulates both JAXB transformations and XSD validation. */
public class XmlTransformer {
    private final JAXBContext context;
    private final Schema schema;

    /**
     * Loads the POJO mapping and the student XSD schema.
     * @throws JAXBException if the POJO mapping cannot be created
     * @throws SAXException if the XSD schema is invalid
     */
    public XmlTransformer() throws JAXBException, SAXException {
        context = JAXBContext.newInstance(Student.class);
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        factory.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        factory.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
        schema = factory.newSchema(XmlTransformer.class.getResource("/student.xsd"));
    }

    /**
     * Reads XML into a student after validating it against the XSD.
     * External entities and DTD loading are disabled for network input.
     *
     * @param xml XML received over the network
     * @return validated student POJO
     * @throws JAXBException if JAXB cannot read the XML or validation fails
     * @throws XMLStreamException if the XML is malformed
     */
    public Student transformToPOJO(InputStream xml) throws JAXBException, XMLStreamException {
        XMLInputFactory inputFactory = XMLInputFactory.newFactory();
        inputFactory.setProperty(XMLInputFactory.SUPPORT_DTD, false);
        inputFactory.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, false);
        XMLStreamReader reader = inputFactory.createXMLStreamReader(xml);
        try {
            Unmarshaller unmarshaller = context.createUnmarshaller();
            unmarshaller.setSchema(schema);
            return (Student) unmarshaller.unmarshal(reader);
        } finally {
            reader.close();
        }
    }

    /**
     * Writes a student POJO as XML and validates the result against the XSD.
     *
     * @param student student to write
     * @return formatted XML text
     * @throws JAXBException if the object does not match the XSD
     */
    public String transformToXML(Student student) throws JAXBException {
        Marshaller marshaller = context.createMarshaller();
        marshaller.setSchema(schema);
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        StringWriter output = new StringWriter();
        marshaller.marshal(student, output);
        return output.toString();
    }
}
