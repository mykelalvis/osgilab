/*
 * Copyright (c) 2010 Dmytro Pishchukhin (http://osgilab.org)
 * This program is made available under the terms of the MIT License.
 */

package org.osgilab.bundles.monitoradmin;

import org.osgi.service.monitor.StatusVariable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Scheduled MonitoringJob
 *
 * @author dmytro.pishchukhin
 */
public class ScheduledMonitoringJob extends AbstractMonitoringJob implements Runnable {
    private int measurementsTaken = 0;
    private ExecutorService executorService;

    protected ScheduledMonitoringJob(MonitoringJobVisitor visitor, String initiator,
                                     String[] statusVariablePaths, int schedule, int count) {
        super(visitor, initiator, statusVariablePaths, schedule, count);
        executorService = Executors.newSingleThreadExecutor();
        executorService.submit(this);
    }

    @Override
    public void cancel() {
        isRunning = false;
        executorService.shutdownNow();
    }

    @Override
    public boolean isHandleUpdateEvent(String path) {
        return false;
    }

    @Override
    public void handleUpdateEvent(String monitorableId, StatusVariable statusVariable) {
        // do nothing
    }

    public void run() {
        while (isRunning()) {
            if (count == 0 || ++measurementsTaken < count) {
                try {
                    for (String path : statusVariablePaths) {
                        StatusVariablePath statusVariablePath = new StatusVariablePath(path);
                        StatusVariable statusVariable = visitor.getStatusVariable(statusVariablePath.getPath());
                        visitor.fireEvent(statusVariablePath.getMonitorableId(), statusVariable, getInitiator());
                    }
                    try {
                        TimeUnit.SECONDS.sleep(schedule);
                    } catch (InterruptedException e) {
                        return;
                    }
                } catch (IllegalArgumentException e) {
                    // it seems that one StatusVariable is unregistered
                    stop();
                }
            } else {
                stop();
            }
        }
    }
}
