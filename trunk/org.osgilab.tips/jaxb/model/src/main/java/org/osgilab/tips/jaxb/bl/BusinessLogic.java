package org.osgilab.tips.jaxb.bl;

import org.osgilab.tips.jaxb.model.PersonesType;
import org.osgilab.tips.jaxb.parser.Parser;

/**
 * @author dmytro.pishchukhin
 */
public class BusinessLogic {
    private Parser parser;

    public BusinessLogic() {
        parser = new Parser();
    }

    public int getPersonesCount() {
        PersonesType persones = parser.parse(getClass().getClassLoader().getResourceAsStream("model.xml"));
        return persones.getPerson().size();
    }
}
