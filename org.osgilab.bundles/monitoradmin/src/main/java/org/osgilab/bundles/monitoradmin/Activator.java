/*
 * Copyright (c) 2010 Dmytro Pishchukhin (http://osgilab.org)
 * This program is made available under the terms of the MIT License.
 */

package org.osgilab.bundles.monitoradmin;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.monitor.MonitorAdmin;
import org.osgi.service.monitor.MonitorListener;

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

        serviceRegistration = bundleContext.registerService(new String[] {MonitorAdmin.class.getName(),
                MonitorListener.class.getName()}, monitorAdmin, null);
    }

    public void stop(BundleContext bundleContext) throws Exception {
        serviceRegistration.unregister();
        serviceRegistration = null;

        monitorAdmin = null;
    }
}
