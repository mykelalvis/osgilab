package org.osgilab.tips.jaxb.impl;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgilab.tips.jaxb.bl.BusinessLogic;

/**
 * @author dmytro.pishchukhin
 */
public class Activator implements BundleActivator {
    public void start(BundleContext bundleContext) throws Exception {
       bundleContext.registerService(BusinessLogic.class.getName(), new BusinessLogic(), null);
    }

    public void stop(BundleContext bundleContext) throws Exception {
        // todo
    }
}
