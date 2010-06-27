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
