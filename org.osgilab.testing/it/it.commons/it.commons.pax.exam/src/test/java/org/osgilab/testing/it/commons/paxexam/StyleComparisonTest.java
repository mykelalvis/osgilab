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

package org.osgilab.testing.it.commons.paxexam;

import org.junit.Assert;
import org.junit.Test;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.Configuration;
import org.osgi.framework.*;
import org.osgi.service.packageadmin.PackageAdmin;
import org.osgi.testing.it.commons.testbundle.service.Echo;
import org.osgi.util.tracker.ServiceTracker;
import org.osgilab.testing.commons.utils.ServiceUtils;

import java.util.concurrent.TimeUnit;

import static org.ops4j.pax.exam.CoreOptions.mavenBundle;
import static org.ops4j.pax.exam.CoreOptions.options;
import static org.osgilab.testing.commons.assertions.BundleAssert.assertBundleAvailable;
import static org.osgilab.testing.commons.assertions.BundleAssert.assertBundleState;
import static org.osgilab.testing.commons.assertions.ServiceAssert.assertServiceAvailable;
import static org.osgilab.testing.commons.assertions.ServiceAssert.assertServiceUnavailable;
import static org.osgilab.testing.commons.utils.BundleUtils.findBundle;
import static org.osgilab.testing.commons.utils.FilterUtils.*;

/**
 * This is JUnit test that shows difference between tests
 * with OSGi assertions/utils and without
 *
 * @author dmytro.pishchukhin
 */
public class StyleComparisonTest extends AbstractTest {
    /**
     * Install test bundle
     *
     * @return config
     */
    @Configuration
    public static Option[] customTestConfiguration() {
        return options(
                mavenBundle().groupId("org.osgilab.testing.it").artifactId("it.commons.test.bundle").
                        version("1.0-SNAPSHOT").noStart()
        );
    }

    /**
     * Test bundle and service without OSGi assertions/utils
     *
     * @throws org.osgi.framework.BundleException
     *          bundle start/stop problems
     * @throws org.osgi.framework.InvalidSyntaxException
     *          filter creation problems
     * @throws InterruptedException wait exception
     */
    @Test
    public void test_Without_OSGiAssertions() throws BundleException, InterruptedException, InvalidSyntaxException {
        ServiceTracker packageAdminTracker = new ServiceTracker(bc, PackageAdmin.class.getName(), null);
        packageAdminTracker.open();
        PackageAdmin packageAdmin = (PackageAdmin) packageAdminTracker.getService();
        Assert.assertNotNull(packageAdmin);
        packageAdminTracker.close();
        Bundle[] bundles = packageAdmin.getBundles("org.osgilab.testing.it.commons.test.bundle", null);
        // asserts that test bundle is installed
        Assert.assertNotNull(bundles);
        Assert.assertTrue(bundles.length > 0);
        // gets bundle instance
        Bundle bundle = bundles[0];
        // asserts that test bundle is resolved
        Assert.assertEquals(Bundle.RESOLVED, bundle.getState());
        ServiceTracker serviceTracker1 = new ServiceTracker(bc, "org.osgi.testing.it.commons.testbundle.service.Echo", null);
        serviceTracker1.open();
        Assert.assertEquals(0, serviceTracker1.size());
        // start bundle
        bundle.start();
        // asserts that test bundle is active
        Assert.assertEquals(Bundle.ACTIVE, bundle.getState());
        // asserts that test service is available within 2 seconds
        Assert.assertNotNull(serviceTracker1.waitForService(2000));
        // asserts that test service with custom properties is available
        ServiceTracker serviceTracker2 = new ServiceTracker(bc, FrameworkUtil.createFilter(
                "(&(" + Constants.OBJECTCLASS + "=org.osgi.testing.it.commons.testbundle.service.Echo)" +
                        "(testkey=testvalue))"), null);
        serviceTracker2.open();
        Assert.assertTrue(serviceTracker2.size() > 0);
        // gets service by class and filter
        Echo echo = (Echo) serviceTracker2.getService();
        // asserts service method call
        Assert.assertEquals("test", echo.echo("test"));
        // stops bundle
        bundle.stop();
        // asserts that test bundle is resolved
        Assert.assertEquals(Bundle.RESOLVED, bundle.getState());
        // asserts that test service is unregistered
        Assert.assertEquals(0, serviceTracker1.size());
    }

    /**
     * The same Test bundle and service with OSGi assertions/utils
     *
     * @throws org.osgi.framework.BundleException
     *          bundle start/stop problems
     * @throws org.osgi.framework.InvalidSyntaxException
     *          filter creation problems
     */
    @Test
    public void test_With_OSGiAssertions() throws BundleException, InvalidSyntaxException {
        // asserts that test bundle is installed
        assertBundleAvailable("org.osgilab.testing.it.commons.test.bundle");
        // asserts that test bundle is resolved
        assertBundleState(Bundle.RESOLVED, "org.osgilab.testing.it.commons.test.bundle");
        // gets bundle instance
        Bundle bundle = findBundle(bc, "org.osgilab.testing.it.commons.test.bundle");
        // asserts that test service is unavailable
        assertServiceUnavailable("org.osgi.testing.it.commons.testbundle.service.Echo");
        // start bundle
        bundle.start();
        // asserts that test bundle is active
        assertBundleState(Bundle.ACTIVE, "org.osgilab.testing.it.commons.test.bundle");
        // asserts that test service is available within 2 seconds
        assertServiceAvailable("org.osgi.testing.it.commons.testbundle.service.Echo", 2, TimeUnit.SECONDS);
        // asserts that test service with custom properties is available
        assertServiceAvailable(and(create(Echo.class), eq("testkey", "testvalue")));
        // gets service by class and filter
        Echo echo = ServiceUtils.getService(bc, Echo.class, eq("testkey", "testvalue"));
        // asserts service method call
        Assert.assertEquals("test", echo.echo("test"));
        // stops bundle
        bundle.stop();
        // asserts that test bundle is resolved
        assertBundleState(Bundle.RESOLVED, "org.osgilab.testing.it.commons.test.bundle");
        // asserts that test service is unregistered
        assertServiceUnavailable(Echo.class);
    }
}
