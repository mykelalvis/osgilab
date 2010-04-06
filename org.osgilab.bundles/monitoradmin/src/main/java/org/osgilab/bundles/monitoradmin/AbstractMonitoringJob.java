/*
 * Copyright (c) 2010 Dmytro Pishchukhin (http://osgilab.org)
 * This program is made available under the terms of the MIT License.
 */

package org.osgilab.bundles.monitoradmin;

import org.osgi.service.monitor.MonitoringJob;
import org.osgi.service.monitor.StatusVariable;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Abstract MonitoringJob
 * 
 * @author dmytro.pishchukhin
 */
public abstract class AbstractMonitoringJob implements MonitoringJob {
    protected MonitoringJobVisitor visitor;
    private String initiator;
    protected Set<String> statusVariablePaths = new HashSet<String>();
    protected boolean isRunning;
    protected int schedule = 0;
    protected int count = 0;

    protected AbstractMonitoringJob(MonitoringJobVisitor visitor, String initiator,
                                    String[] statusVariablePaths, int schedule, int count) {
        this.visitor = visitor;
        this.initiator = initiator;
        this.statusVariablePaths.addAll(Arrays.asList(statusVariablePaths));
        this.schedule = schedule;
        this.count = count;
    }

    protected AbstractMonitoringJob(MonitoringJobVisitor visitor, String initiator,
                                    String[] statusVariablePaths, int count) {
        this.visitor = visitor;
        this.initiator = initiator;
        this.statusVariablePaths.addAll(Arrays.asList(statusVariablePaths));
        this.count = count;
    }

    public synchronized void stop() {
        if (isRunning) {
            isRunning = false;
            visitor.cancelJob(this);
        }
    }

    public String getInitiator() {
        return initiator;
    }

    public String[] getStatusVariableNames() {
        return statusVariablePaths.toArray(new String[statusVariablePaths.size()]);
    }

    public int getSchedule() {
        return schedule;
    }

    public int getReportCount() {
        return count;
    }

    public boolean isLocal() {
        return true;
    }

    public boolean isRunning() {
        return isRunning;
    }

    /**
     * Cancel job internaly
     */
    public abstract void cancel();

    /**
     * Does job handle StatusVariable update event
     * @param path StatusVariable path
     * @return <code>true</code> - handles, otherwise - <code>false</code>
     */
    public abstract boolean isHandleUpdateEvent(String path);

    /**
     * Handle StatusVariable update event
     * @param monitorableId monitorableId
     * @param statusVariable statusVariable
     */
    public abstract void handleUpdateEvent(String monitorableId, StatusVariable statusVariable);
}
