/*
 * Copyright (c) 2010 Dmytro Pishchukhin (http://osgilab.org)
 * This program is made available under the terms of the MIT License.
 */

package org.osgilab.bundles.monitoradmin;

import org.osgi.framework.Constants;

import java.util.regex.Pattern;

/**
 * Utils class
 *
 * @author dmytro.pishchukhin
 */
public class Utils {
    /**
     * MonitorableId and Status variable name validate pattern (OSGi core 1.3.2: symbolic-name)
     */
    private static final Pattern ID_VALIDATE_PATTERN = Pattern.compile("((\\w|_|-)+)(\\.(\\w|_|-)+)*");

    public static String createServicePidFilter(String monitorableId) {
        StringBuilder builder = new StringBuilder();
        builder.append('(');
        builder.append(Constants.SERVICE_PID);
        builder.append('=');
        builder.append(monitorableId);
        builder.append(')');
        return builder.toString();
    }

    public static boolean validateId(String id) {
        return ID_VALIDATE_PATTERN.matcher(id).matches();
    }
}
