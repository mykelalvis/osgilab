package org.osgilab.bundles.monitoradmin.mocks;

import org.osgilab.bundles.monitoradmin.LogVisitor;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author dpishchukhin
 */
public class MockLogVisitor implements LogVisitor {
    private static final Logger LOG = Logger.getLogger(MockLogVisitor.class.getName());

    public void debug(String message, Throwable throwable) {
        LOG.log(Level.FINE, message, throwable);
    }

    public void info(String message, Throwable throwable) {
        LOG.log(Level.INFO, message, throwable);
    }

    public void warning(String message, Throwable throwable) {
        LOG.log(Level.WARNING, message, throwable);
    }

    public void error(String message, Throwable throwable) {
        LOG.log(Level.SEVERE, message, throwable);
    }
}
