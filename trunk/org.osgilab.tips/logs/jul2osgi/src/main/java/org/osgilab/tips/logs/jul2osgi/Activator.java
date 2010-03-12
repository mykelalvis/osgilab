package org.osgilab.tips.logs.jul2osgi;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.log.LogService;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

import java.util.logging.Logger;

/**
 * @author dmytro.pishchukhin
 */
public class Activator implements BundleActivator {
    private static final Logger LOG = Logger.getLogger(Activator.class.getName());

    private BundleContext bc;
    private ServiceTracker logServiceTracker;

    public void start(BundleContext bundleContext) throws Exception {
        LOG.entering(getClass().getName(), "start");
        try {
            bc = bundleContext;

            logServiceTracker = new ServiceTracker(bundleContext, LogService.class.getName(), new LogServiceCustomizer());
            logServiceTracker.open();

        } finally {
            LOG.exiting(getClass().getName(), "start");
        }
    }

    public void stop(BundleContext bundleContext) throws Exception {
        LOG.entering(getClass().getName(), "stop");
        try {
            logServiceTracker.close();
            logServiceTracker = null;

            bc = null;
        } finally {
            LOG.exiting(getClass().getName(), "stop");
        }
    }

    private class LogServiceCustomizer implements ServiceTrackerCustomizer {
        public Object addingService(ServiceReference serviceReference) {
            return null;  // todo
        }

        public void modifiedService(ServiceReference serviceReference, Object o) {
            // todo
        }

        public void removedService(ServiceReference serviceReference, Object o) {
            // todo
        }
    }
}
