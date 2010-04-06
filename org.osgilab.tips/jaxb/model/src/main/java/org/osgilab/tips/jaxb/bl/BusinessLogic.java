/*
 * Copyright (c) 2010 Dmytro Pishchukhin (http://osgilab.org)
 * This program is made available under the terms of the MIT License.
 */

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
