/*
 * Copyright (c) 2010 Dmytro Pishchukhin (http://osgilab.org)
 * This program is made available under the terms of the MIT License.
 */

package org.osgilab.bundles.monitoradmin;

import org.osgi.service.monitor.StatusVariable;

import java.util.HashMap;
import java.util.Map;

/**
 * Subscription MonitoringJob
 *
 * @author dmytro.pishchukhin
 */
public class SubscriptionMonitoringJob extends AbstractMonitoringJob {
    private Map<String, Integer> countStatesMap = new HashMap<String, Integer>();

    protected SubscriptionMonitoringJob(MonitoringJobVisitor visitor, String initiator,
                                        String[] statusVariablePaths, int count) {
        super(visitor, initiator, statusVariablePaths, count);
        // initialize counts map
        for (String statusVariablePath : statusVariablePaths) {
            countStatesMap.put(statusVariablePath, 0);
        }
    }

    @Override
    public void cancel() {
        // do nothing
    }

    @Override
    public boolean isHandleUpdateEvent(String path) {
        return isRunning() && statusVariablePaths.contains(path);
    }

    @Override
    public void handleUpdateEvent(String monitorableId, StatusVariable statusVariable) {
        StatusVariablePath path = new StatusVariablePath(monitorableId, statusVariable.getID());
        int statusVariableChangesCount = countStatesMap.get(path.getPath());
        if ((statusVariableChangesCount + 1) == count) {
            visitor.fireEvent(monitorableId, statusVariable, getInitiator());
            countStatesMap.put(path.getPath(), 0);
        } else {
            countStatesMap.put(path.getPath(), statusVariableChangesCount + 1);
        }
    }
}