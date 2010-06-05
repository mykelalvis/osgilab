/*
 * Copyright (c) 2010 Dmytro Pishchukhin (http://osgilab.org)
 * This program is made available under the terms of the MIT License.
 */

package org.osgilab.tips.shell.equinox;

import org.eclipse.osgi.framework.console.CommandProvider;
import org.osgi.framework.*;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

import java.io.PrintWriter;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Equinox shell adapter activator
 *
 * @author dmytro.pishchukhin
 */
public class Activator implements BundleActivator {
    private static final Logger LOG = Logger.getLogger(Activator.class.getName());

    private static final String COMMANDS_DESCRIPTION_PROPERTY = "org.osgilab.tips.shell.commands";
    private static final String GROUP_NAME_PROPERTY = "org.osgilab.tips.shell.group.name";

    /**
     * Equinox shell API does not support group id.
     * Filter requires shell commands description and group name
     */
    private static final String SHELL_COMMANDS_SERVICE_FILTER = "(&" +
            "(" + COMMANDS_DESCRIPTION_PROPERTY + "=*)" +
            "(" + GROUP_NAME_PROPERTY + "=*)" +
            ")";

    /**
     * Bundle Context instance
     */
    private BundleContext bc;
    /**
     * Command provides service tracker
     */
    private ServiceTracker shellCommandsTracker;

    private Map<ServiceReference, ServiceRegistration> commandRegistrations = new HashMap<ServiceReference, ServiceRegistration>();

    public void start(BundleContext context) throws Exception {
        bc = context;
        shellCommandsTracker = new ServiceTracker(bc, bc.createFilter(SHELL_COMMANDS_SERVICE_FILTER),
                new ShellCommandsCustomizer());
        shellCommandsTracker.open();
    }

    public void stop(BundleContext context) throws Exception {
        shellCommandsTracker.close();
        shellCommandsTracker = null;

        bc = null;
    }

    /**
     * Validate Command method
     *
     * @param service     service instance
     * @param commandName command method name
     * @return <code>true</code> if method is peresent in service, <code>public</code> and
     *         has params <code>PrintStream</code> and <code>String[]</code>, otherwise - <code>false</code>
     */
    private boolean isValidCommandMethod(Object service, String commandName) {
        try {
            service.getClass().getMethod(commandName, PrintWriter.class, String[].class);
            return true;
        } catch (NoSuchMethodException e) {
            return false;
        }
    }

    /**
     * Command provides service tracker customizer
     */
    private class ShellCommandsCustomizer implements ServiceTrackerCustomizer {
        public Object addingService(ServiceReference reference) {
            Object groupName = reference.getProperty(GROUP_NAME_PROPERTY);
            // if property value null or not String - ignore service
            if (groupName == null || !(groupName instanceof String)) {
                LOG.warning(GROUP_NAME_PROPERTY + " property is null or invalid. Ignore service");
                return null;
            }
            // get commands description property
            Object commandsDescription = reference.getProperty(COMMANDS_DESCRIPTION_PROPERTY);
            // if property value null or not String[][] - ignore service
            if (commandsDescription == null) {
                LOG.warning(COMMANDS_DESCRIPTION_PROPERTY + " property is null. Ignore service");
                return null;
            } else if (!(commandsDescription instanceof String[][])) {
                LOG.warning(COMMANDS_DESCRIPTION_PROPERTY + " property has wrong format: not String[][]");
                return null;
            } else {
                Object service = bc.getService(reference);
                // get service ranking propety. if not null - use it on Command services registration
                String[][] commands = (String[][]) commandsDescription;
                Map<String, String> commandMap = new HashMap<String, String>();
                for (String[] commandInfo : commands) {
                    // if command info is invalid - ignore command
                    if (commandInfo != null) {
                        if (commandInfo.length != 2) {
                            LOG.warning(COMMANDS_DESCRIPTION_PROPERTY + " property has wrong format: not String[][]");
                            continue;
                        }
                        String commandName = commandInfo[0];
                        String commandHelp = commandInfo[1];
                        // if command info links to wrong method - ignore command
                        if (isValidCommandMethod(service, commandName)) {
                            commandMap.put(commandName, commandHelp);
                        }
                    }
                }
                if (!commandMap.isEmpty()) {
                    Dictionary<String, Object> props = new Hashtable<String, Object>();
                    Integer ranking = (Integer) reference.getProperty(Constants.SERVICE_RANKING);
                    Long serviceId = (Long) reference.getProperty(Constants.SERVICE_ID);
                    if (ranking != null) {
                        props.put(Constants.SERVICE_RANKING, ranking);
                    }
                    try {
                        // generate class
                        Object commandsProvider = EquinoxCommandProviderGenerator.generate(service, (String) groupName,
                                commandMap, serviceId.toString());
                        commandRegistrations.put(reference,
                                bc.registerService(CommandProvider.class.getName(), commandsProvider, props));
                    } catch (Exception e) {
                        LOG.log(Level.WARNING, "Unable to initialize group: " + groupName, e);
                    }
                }
                return service;
            }
        }

        public void modifiedService(ServiceReference reference, Object service) {
            // ignore
        }

        public void removedService(ServiceReference reference, Object service) {
            // unregister CommandGroup services that belongs to this service registration
            Long serviceId = (Long) reference.getProperty(Constants.SERVICE_ID);
            // detach class
            EquinoxCommandProviderGenerator.clean(serviceId.toString());
            ServiceRegistration registration = commandRegistrations.remove(reference);
            if (registration != null) {
                registration.unregister();
            }
            bc.ungetService(reference);
        }
    }
}