/*
 * Copyright (c) 2009 Dmytro Pishchukhin (http://osgilab.org)
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

package org.osgilab.bundles.monitoradmin;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.monitor.MonitorAdmin;

/**
 * Monitor Admin activator
 *
 * @author dmytro.pishchukhin
 */
public class Activator implements BundleActivator {
    // monitor admin instance
    private MonitorAdminImpl monitorAdmin;

    // monitor admin service registration
    private ServiceRegistration serviceRegistration;

    public void start(BundleContext bundleContext) throws Exception {
        monitorAdmin = new MonitorAdminImpl(bundleContext);

        serviceRegistration = bundleContext.registerService(MonitorAdmin.class.getName(), monitorAdmin, null);
    }

    public void stop(BundleContext bundleContext) throws Exception {
        serviceRegistration.unregister();
        serviceRegistration = null;

        monitorAdmin = null;
    }
}