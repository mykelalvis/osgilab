/*
 * Copyright (c) 2010 Dmytro Pishchukhin (http://knowhowlab.org)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.osgilab.bundles.monitoradmin.mocks;

import org.osgi.framework.Constants;
import org.osgi.framework.ServiceReference;
import org.osgi.service.event.Event;
import org.osgi.service.monitor.Monitorable;
import org.osgilab.bundles.monitoradmin.OsgiVisitor;

import java.util.*;

/**
 * @author dpishchukhin
 */
public class MockOsgiVisitor implements OsgiVisitor {
    private Map<ServiceReference, Monitorable> serviceReferences = new HashMap<ServiceReference, Monitorable>();

    private List<Event> events = new ArrayList<Event>();

    public Event[] getPostedEvents() {
        return events.toArray(new Event[events.size()]);
    }

    public void cleanPostedEvents() {
        events.clear();
    }

    public void setReferences(Map<ServiceReference, Monitorable> references) {
        serviceReferences.clear();
        serviceReferences.putAll(references);
    }

    public Monitorable getService(ServiceReference reference) {
        return serviceReferences.get(reference);
    }

    public ServiceReference[] findMonitorableReferences(String monitorableId) {
        Set<ServiceReference> references = serviceReferences.keySet();
        List<ServiceReference> result = new ArrayList<ServiceReference>();
        for (ServiceReference reference : references) {
            if (monitorableId == null || reference.getProperty(Constants.SERVICE_PID).equals(monitorableId)) {
                result.add(reference);
            }
        }
        return result.toArray(new ServiceReference[result.size()]);
    }

    public void postEvent(Event event) {
        events.add(event);
    }
}
