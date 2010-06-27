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

import org.junit.Test;
import org.osgi.framework.Bundle;
import org.osgi.framework.Version;

import static org.osgilab.testing.commons.assertions.BundleAssert.*;

/**
 * BundleAssert test
 *
 * @author dmytro.pishchukhin
 */
public class BundlesTest extends AbstractTest {
    @Test
    public void simpleTest() {
        // assert bundle with id=1 is installed into OSGi framework
        assertBundleAvailable(1);
        // assert bundle with id=1 active
        assertBundleState(Bundle.ACTIVE, 1);
        // assert bundle with id=100 is not installed into OSGi framework
        assertBundleUnavailable(100);
        // assert bundle with symbolic name "org.osgilab.testing.commons" is installed into OSGi framework
        assertBundleAvailable("org.osgilab.testing.commons");
        // assert bundle with symbolic name "org.osgilab.testing.commons" and version "1.0.0"
        // is installed into OSGi framework
        assertBundleAvailable("org.osgilab.testing.commons", new Version("1.0.0"));
        // assert bundle with symbolic name "org.osgilab.testing.commons" and version "1.0.0"
        // is not installed into OSGi framework
        assertBundleUnavailable("org.osgilab.testing.commons", new Version("2.0.0"));
    }
}
