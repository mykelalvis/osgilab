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

import org.junit.Before;
import org.junit.runner.RunWith;
import org.knowhowlab.osgi.testing.commons.assertions.OSGiAssert;
import org.ops4j.pax.exam.Inject;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.Configuration;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;
import org.osgi.framework.BundleContext;

import static org.ops4j.pax.exam.CoreOptions.*;

/**
 * Abstract test with all initializations
 *
 * @author dmytro.pishchukhin
 */
@RunWith(JUnit4TestRunner.class)
public abstract class AbstractTest {
    /**
     * Injected BundleContext
     */
    @Inject
    protected BundleContext bc;

    /**
     * Runner config
     *
     * @return config
     */
    @Configuration
    public static Option[] configuration() {
        return options(
                // list of frameworks to test
                allFrameworks(),
                // list of bundles that should be installd
                provision(
                        mavenBundle().groupId("org.osgi").artifactId("org.osgi.compendium").version("4.0.1"),
                        mavenBundle().groupId("org.knowhowlab.osgi.testing").artifactId("commons").version("1.0.1-SNAPSHOT")
                )
        );
    }


    /**
     * Init OSGiAssert with BundleContext
     */
    @Before
    public void initOSGiAssert() {
        OSGiAssert.init(bc);
    }
}
