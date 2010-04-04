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

import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.springframework.osgi.mock.MockBundleContext;

/**
 * @author dmytro.pishchukhin
 */
public class WritableMockBundleContext extends MockBundleContext {
    private ServiceReference[] references;

    public void setReferences(ServiceReference[] references) {
        this.references = references;
    }

    @Override
    public ServiceReference[] getServiceReferences(String clazz, String filter) throws InvalidSyntaxException {
        return references;
    }
}
