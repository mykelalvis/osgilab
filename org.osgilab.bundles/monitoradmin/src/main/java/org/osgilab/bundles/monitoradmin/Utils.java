/*
 * Copyright (c) 2010 Dmytro Pishchukhin (http://osgilab.org)
 *
 * This file is part of OSGi Lab project (http://code.google.com/p/osgilab/).
 *
 * OSGi Lab modules are free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * OSGi Lab modules are distributed in the hope that they will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with OSGi Lab modules.  If not, see <http://www.gnu.org/licenses/lgpl.txt>.
 */

package org.osgilab.bundles.monitoradmin;

import org.osgi.framework.Constants;

import java.util.regex.Pattern;

/**
 * Utils class
 *
 * @author dmytro.pishchukhin
 *
 * FIXME: too many calls of Regexp for validation (sometimes 2-3 times for every MonitorAdmin method)
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

    /**
     * Parse monitorableId from path
     *
     * @param path status variable path
     * @return non-nullable monitorableId value
     * @throws IllegalArgumentException path is <code>null</code> or invalid
     */
    public static String parseMonitorableIdFromPath(String path) throws IllegalArgumentException {
        return parseIds(path)[0];
    }

    /**
     * Parse statusVariableId from path
     *
     * @param path status variable path
     * @return non-nullable statusVariableId value
     * @throws IllegalArgumentException path is <code>null</code> or invalid
     */
    public static String parseStatusVariableIdFromPath(String path) throws IllegalArgumentException {
        return parseIds(path)[1];
    }

    /**
     * Parse path and return non-nullable array (lenght = 2) with non-empty IDs values:
     * <li>resultArray[0] = monitorableId
     * <li>resultArray[1] = statusVariableId
     *
     * @param path status variable path
     * @return non-nullable array with ids
     * @throws IllegalArgumentException path is <code>null</code> or invalid
     * (contains more or less than one separator '/' or parsed IDs are empty or invalid)
     */
    protected static String[] parseIds(String path) throws IllegalArgumentException {
        if (path == null) {
            throw new IllegalArgumentException("Path is null");
        }
        String[] parts = path.split("/");
        if (parts.length != 2 || parts[0].isEmpty() || !validateId(parts[0]) || parts[1].isEmpty() || !validateId(parts[0])) {
            throw new IllegalArgumentException("Path value is invalid: " + path);
        }
        return parts;
    }

    public static boolean validateId(String id) {
        return ID_VALIDATE_PATTERN.matcher(id).matches();
    }
}
