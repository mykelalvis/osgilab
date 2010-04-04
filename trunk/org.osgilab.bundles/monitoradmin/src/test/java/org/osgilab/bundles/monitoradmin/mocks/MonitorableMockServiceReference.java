/*
 * Copyright (c) 2010 Dmytro Pishchukhin (http://osgilab.org)
 *
 * This file is part of OSGi Lab project (http://code.google.com/p/osgilab/).
 *
 * OSGi Lab modules are free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * OSGi Lab modules are distributed in the hope that they will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with OSGi Lab modules.  If not, see <http://www.gnu.org/licenses/lgpl.txt>.
 */

package org.osgilab.bundles.monitoradmin.mocks;

import org.osgi.framework.Constants;
import org.osgi.service.monitor.Monitorable;
import org.springframework.osgi.mock.MockServiceReference;

import java.util.Hashtable;

/**
 * @author dmytro.pishchukhin
 */
public class MonitorableMockServiceReference extends MockServiceReference {
    public MonitorableMockServiceReference(String pid) {
        super(new String[] {Monitorable.class.getName()});
        Hashtable props = new Hashtable();
        props.put(Constants.SERVICE_PID, pid);
        setProperties(props);
    }
}
