package org.osgilab.tips.jaxb.parser;

import org.osgilab.tips.jaxb.model.PersonesType;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import java.io.InputStream;

/**
 * @author dmytro.pishchukhin
 */
public class Parser {
    private JAXBContext context;

    public PersonesType parse(InputStream in)  {
        try {
            context = JAXBContext.newInstance("org.osgilab.tips.jaxb.model");
            JAXBElement<PersonesType> element = (JAXBElement<PersonesType>) context.createUnmarshaller().unmarshal(in);
            return element.getValue();
        } catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }
}
