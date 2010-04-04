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

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.service.monitor.MonitorAdmin;
import org.osgi.service.monitor.Monitorable;
import org.osgilab.bundles.monitoradmin.mocks.MonitorableMockServiceReference;
import org.osgilab.bundles.monitoradmin.mocks.WritableMockBundleContext;
import org.springframework.osgi.mock.MockBundleContext;
import org.springframework.osgi.mock.MockServiceReference;

/**
 * @author dmytro.pishchukhin
 */
public class MonitorAdminImplTest {
    @Before
    public void init() {
    }

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
