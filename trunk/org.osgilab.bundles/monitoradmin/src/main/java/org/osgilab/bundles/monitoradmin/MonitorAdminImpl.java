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

package org.osgilab.bundles.monitoradmin;

import org.osgi.framework.Constants;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.service.monitor.MonitorAdmin;
import org.osgi.service.monitor.Monitorable;
import org.osgi.service.monitor.MonitoringJob;
import org.osgi.service.monitor.StatusVariable;

import java.util.*;

/**
 * MonitorAdmin implementation
 *
 * @author dmytro.pishchukhin
 */
public class MonitorAdminImpl implements MonitorAdmin {
    private BundleContext bc;

    public MonitorAdminImpl(BundleContext bc) {
        this.bc = bc;
    }

    public StatusVariable getStatusVariable(String path)
            throws IllegalArgumentException, SecurityException {
        return null;  // todo
    }

        public String[] getMonitorableNames() {
        // sorted set that contains Monitorable SERVICE_PIDs
        SortedSet<String> names = new TreeSet<String>();

        ServiceReference[] serviceReferences = null;
        try {
            serviceReferences = bc.getServiceReferences(Monitorable.class.getName(), null);
        } catch (InvalidSyntaxException e) {
            // illegal state as filter is null
        }

        if (serviceReferences != null) {
            for (ServiceReference serviceReference : serviceReferences) {
                String pid = (String) serviceReference.getProperty(Constants.SERVICE_PID);
                if (pid != null) {
                    names.add(pid);
                }
            }
        }

        return names.toArray(new String[names.size()]);
    }

    public StatusVariable[] getStatusVariables(String monitorableId)
            throws IllegalArgumentException {
        return new StatusVariable[0];  // todo
    }

    public String[] getStatusVariableNames(String monitorableId)
            throws IllegalArgumentException {
        return new String[0];  // todo
    }

    public void switchEvents(String path, boolean on)
            throws IllegalArgumentException, SecurityException {
        // todo
    }

    public boolean resetStatusVariable(String path)
            throws IllegalArgumentException, SecurityException {
        return false;  // todo
    }

    public String getDescription(String path)
            throws IllegalArgumentException, SecurityException {
        return null;  // todo
    }

    public MonitoringJob startScheduledJob(String initiator,
            String[] statusVariables, int schedule, int count)
            throws IllegalArgumentException, SecurityException {
        return null;  // todo
    }

    public MonitoringJob startJob(String initiator, String[] statusVariables, int count)
            throws IllegalArgumentException, SecurityException {
        return null;  // todo
    }

    public MonitoringJob[] getRunningJobs() {
        return new MonitoringJob[0];  // todo
    }
}
