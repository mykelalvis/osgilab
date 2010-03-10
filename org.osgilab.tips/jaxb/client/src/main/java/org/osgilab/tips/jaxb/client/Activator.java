package org.osgilab.tips.jaxb.client;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgilab.tips.jaxb.bl.BusinessLogic;

/**
 * @author dmytro.pishchukhin
 */
public class Activator implements BundleActivator {
    public void start(BundleContext bundleContext) throws Exception {
        int personesCount = new BusinessLogic().getPersonesCount();
        System.out.println("personesCount = " + personesCount);

        ServiceReference reference = bundleContext.getServiceReference(BusinessLogic.class.getName());
        BusinessLogic bl = (BusinessLogic) bundleContext.getService(reference);
        personesCount = bl.getPersonesCount();
        System.out.println("personesCount = " + personesCount);
    }

    public void stop(BundleContext bundleContext) throws Exception {
        // todo
    }
}
