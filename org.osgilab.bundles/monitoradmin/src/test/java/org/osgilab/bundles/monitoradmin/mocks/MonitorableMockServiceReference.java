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

package org.osgilab.bundles.monitoradmin.mocks;

import org.osgi.framework.Constants;
import org.osgi.service.monitor.Monitorable;
import org.springframework.osgi.mock.MockServiceReference;

import java.util.Hashtable;

/**
 * @author dmytro.pishchukhin
 */
public class MonitorableMockServiceReference extends MockServiceReference {
    public MonitorableMockServiceReference(String pid) {
        super(new String[]{Monitorable.class.getName()});
        Hashtable<String, String> props = new Hashtable<String, String>();
        props.put(Constants.SERVICE_PID, pid);
        setProperties(props);
    }
}
