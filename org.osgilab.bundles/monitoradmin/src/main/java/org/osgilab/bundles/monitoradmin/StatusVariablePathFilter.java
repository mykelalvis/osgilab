/*
 * Copyright (c) 2010 Dmytro Pishchukhin (http://osgilab.org)
 * This program is made available under the terms of the MIT License.
 */

package org.osgilab.bundles.monitoradmin;

/**
 * StatusVariable path filter with '*' wildcard support
 *
 * @author dmytro.pishchukhin
 */
public class StatusVariablePathFilter extends StatusVariablePath {
    public StatusVariablePathFilter(String path) throws IllegalArgumentException {
        String[] ids = parseIds(path);
        this.path = path;
        monitorableId = ids[0].replace("*", "");
        statusVariableId = ids[1].replace("*", "");
    }

    @Override
    protected boolean validateId(String id) {
        return Utils.validatePathFilterId(id);
    }

    public boolean isIncluded(String monitorableId, String statusVariableId) {
        return monitorableId.startsWith(this.monitorableId) && statusVariableId.startsWith(this.statusVariableId);
    }
}
