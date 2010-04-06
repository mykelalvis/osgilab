/*
 * Copyright (c) 2010 Dmytro Pishchukhin (http://osgilab.org)
 * This program is made available under the terms of the MIT License.
 */

package org.osgilab.bundles.monitoradmin.mocks;

import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.springframework.osgi.mock.MockBundleContext;

/**
 * @author dmytro.pishchukhin
 */
public class WritableMockBundleContext extends MockBundleContext {
    private ServiceReference[] references;

    public void setReferences(ServiceReference[] references) {
        this.references = references;
    }

    @Override
    public ServiceReference[] getServiceReferences(String clazz, String filter) throws InvalidSyntaxException {
        return references;
    }
}
