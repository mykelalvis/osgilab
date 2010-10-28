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

package org.knowhowlab.osgi.tips.activation.activator;

import org.knowhowlab.osgi.tips.activation.core.Echo;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.prefs.PreferencesService;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

import java.util.Dictionary;
import java.util.Hashtable;

/**
 * @author dpishchukhin
 */
public class Activator implements BundleActivator, Echo {
    private ServiceTracker serviceTracker;
    private BundleContext bc;
    private ServiceRegistration registration;

    public void start(BundleContext context) throws Exception {
        bc = context;
        serviceTracker = new ServiceTracker(context, PreferencesService.class.getName(), new Customizer());
        serviceTracker.open();
    }

    public void stop(BundleContext context) throws Exception {
        serviceTracker.close();
        serviceTracker = null;
    }

    public String echo(String str) {
        return str;
    }

    private class Customizer implements ServiceTrackerCustomizer {

        public Object addingService(ServiceReference reference) {
            System.out.println("PreferencesService is linked");

            Dictionary<String, String> props = new Hashtable<String, String>();
            props.put(ECHO_TYPE_PROP, "BundleActivator");
            registration = bc.registerService(Echo.class.getName(), Activator.this, props);

            return bc.getService(reference);
        }

        public void modifiedService(ServiceReference reference, Object service) {
        }

        public void removedService(ServiceReference reference, Object service) {
            registration.unregister();
            System.out.println("PreferencesService is unlinked");
        }
    }
}
