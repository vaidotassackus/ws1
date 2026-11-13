package lt.example.xmltask;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.net.URL;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.validation.SchemaFactory;
import javax.xml.XMLConstants;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

class XmlSchemaTest {
    @Test
    void sampleXmlValidatesAgainstDtd() throws Exception {
        URL xml = getClass().getResource("/student.xml");
        assertNotNull(xml);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(true);
        var builder = factory.newDocumentBuilder();
        builder.setErrorHandler(new DefaultHandler() {
            @Override
            public void error(org.xml.sax.SAXParseException exception) throws SAXException {
                throw exception;
            }

            @Override
            public void fatalError(org.xml.sax.SAXParseException exception) throws SAXException {
                throw exception;
            }
        });
        builder.parse(xml.toExternalForm());
    }

    @Test
    void sampleXmlValidatesAgainstXsd() throws Exception {
        URL xml = getClass().getResource("/student.xml");
        URL xsd = getClass().getResource("/student.xsd");
        assertNotNull(xml);
        assertNotNull(xsd);
        var schema = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(xsd);
        schema.newValidator().validate(new javax.xml.transform.stream.StreamSource(xml.toExternalForm()));
    }
}
