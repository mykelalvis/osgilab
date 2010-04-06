/*
 * Copyright (c) 2010 Dmytro Pishchukhin (http://osgilab.org)
 * This program is made available under the terms of the MIT License.
 */

package org.osgilab.bundles.monitoradmin;

import org.osgi.service.monitor.StatusVariable;

/**
 * Scheduled MonitoringJob
 *
 * @author dmytro.pishchukhin
 */
public class ScheduledMonitoringJob extends AbstractMonitoringJob {
    protected ScheduledMonitoringJob(MonitoringJobVisitor visitor, String initiator,
                                     String[] statusVariablePaths, int schedule, int count) {
        super(visitor, initiator, statusVariablePaths, schedule, count);
    }

    @Override
    public void cancel() {
        // todo
    }

    @Override
    public boolean isHandleUpdateEvent(String path) {
        return false;
    }

    @Override
    public void handleUpdateEvent(String monitorableId, StatusVariable statusVariable) {
        // do nothing
    }
}
