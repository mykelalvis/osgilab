/*
 * Copyright (c) 2010 Dmytro Pishchukhin (http://osgilab.org)
 * This program is made available under the terms of the MIT License.
 */

package org.osgilab.bundles.monitoradmin;

/**
 * Status Variable path
 *
 * @author dmytro.pishchukhin
 */
public class StatusVariablePath {
    private String monitorableId;
    private String statusVariableId;

    /**
     * Initialize object and parse input path value
     * @param path path value
     * @throws IllegalArgumentException path is <code>null</code> or invalid
     */
    public StatusVariablePath(String path) throws IllegalArgumentException {
        String[] ids = parseIds(path);
        monitorableId = ids[0];
        statusVariableId = ids[1];
    }

    public String getMonitorableId() {
        return monitorableId;
    }

    public String getStatusVariableId() {
        return statusVariableId;
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
    private String[] parseIds(String path) throws IllegalArgumentException {
        if (path == null) {
            throw new IllegalArgumentException("Path is null");
        }
        String[] parts = path.split("/");
        if (parts.length != 2 || !Utils.validateId(parts[0]) || !Utils.validateId(parts[1])) {
            throw new IllegalArgumentException("Path value is invalid: " + path);
        }
        return parts;
    }
}
