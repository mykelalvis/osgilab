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

package org.knowhowlab.osgi.testing.it.commons.paxexam;

import org.junit.Test;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.service.packageadmin.PackageAdmin;
import org.knowhowlab.osgi.testing.commons.assertions.ServiceAssert;
import org.knowhowlab.osgi.testing.commons.utils.FilterUtils;

/**
 * ServiceAssert test
 *
 * @author dmytro.pishchukhin
 */
public class ServicesTest extends AbstractTest {
    @Test
    public void simpleTest() throws InvalidSyntaxException {
        // assert PackageAdmin service is available in OSGi registry
        ServiceAssert.assertServiceAvailable(PackageAdmin.class);
        // assert MonitorAdmin service is unavailable in OSGi registry
        ServiceAssert.assertServiceUnavailable("org.osgi.service.monitor.MonitorAdmin");

        // assert PackageAdmin service is available in OSGi registry
        ServiceAssert.assertServiceAvailable(FilterUtils.create(PackageAdmin.class));
        // assert MonitorAdmin service is unavailable in OSGi registry
        ServiceAssert.assertServiceUnavailable(FilterUtils.create("org.osgi.service.monitor.MonitorAdmin"));
    }
}
