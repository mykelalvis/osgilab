/*
 * Copyright (c) 2010 Dmytro Pishchukhin (http://osgilab.org)
 * This program is made available under the terms of the MIT License.
 */

package org.osgilab.bundles.monitoradmin;

import org.junit.Assert;
import org.junit.Test;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.monitor.MonitorAdmin;
import org.osgilab.bundles.monitoradmin.mocks.MonitorableMockServiceReference;
import org.osgilab.bundles.monitoradmin.mocks.WritableMockBundleContext;
import org.springframework.osgi.mock.MockBundleContext;

/**
 * @author dmytro.pishchukhin
 */
public class MonitorAdminImplTest {
    @Test
    public void testGetMonitorableNames_NoMonitorableAvailable() throws Exception {
        BundleContext bc = new MockBundleContext();
        MonitorAdmin monitorAdmin = new MonitorAdminImpl(bc);

        String[] monitorableNames = monitorAdmin.getMonitorableNames();
        Assert.assertNotNull(monitorableNames);
        Assert.assertEquals(0, monitorableNames.length);
    }

    @Test
    public void testGetMonitorableNames_MonitorableAvailable() throws Exception {
        WritableMockBundleContext bc = new WritableMockBundleContext();
        bc.setReferences(new ServiceReference[] {
                new MonitorableMockServiceReference("com.acme.pid2"),
                new MonitorableMockServiceReference("com.acme.pid3"),
                new MonitorableMockServiceReference("com.acme.pid1")
        });

        MonitorAdmin monitorAdmin = new MonitorAdminImpl(bc);

        String[] monitorableNames = monitorAdmin.getMonitorableNames();
        Assert.assertNotNull(monitorableNames);
        Assert.assertEquals(3, monitorableNames.length);
        Assert.assertEquals("com.acme.pid1", monitorableNames[0]);
        Assert.assertEquals("com.acme.pid2", monitorableNames[1]);
        Assert.assertEquals("com.acme.pid3", monitorableNames[2]);
    }
}
