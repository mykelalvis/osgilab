package org.osgilab.bundles.monitoradmin;

import org.osgi.framework.ServiceReference;
import org.osgi.service.event.Event;
import org.osgi.service.monitor.Monitorable;

/**
 * OSGi visitor interface
 *
 * @author dpishchukhin
 */
public interface OsgiVisitor {
    Monitorable getService(ServiceReference reference);

    ServiceReference[] findMonitorableReferences(String monitorableId);

    void postEvent(Event event);
}
