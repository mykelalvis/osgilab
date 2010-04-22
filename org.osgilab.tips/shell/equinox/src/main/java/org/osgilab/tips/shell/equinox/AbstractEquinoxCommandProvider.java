/*
 * Copyright (c) 2010 Dmytro Pishchukhin (http://osgilab.org)
 * This program is made available under the terms of the MIT License.
 */

package org.osgilab.tips.shell.equinox;

import org.eclipse.osgi.framework.console.CommandInterpreter;
import org.eclipse.osgi.framework.console.CommandProvider;

import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Abstract class of Equinox Command Provider
 *
 * @author dmytro.pishchukhin
 */
public abstract class AbstractEquinoxCommandProvider implements CommandProvider {
    private static final Logger LOG = Logger.getLogger(AbstractEquinoxCommandProvider.class.getName());

    private String groupName;
    private Object service;
    private SortedSet<String> commandHelps = new TreeSet<String>();

    public AbstractEquinoxCommandProvider(Object service, String groupName, Set<String> commandHelps) {
        this.groupName = groupName;
        this.service = service;
        this.commandHelps.addAll(commandHelps);
    }

    public String getHelp() {
        StringBuilder builder = new StringBuilder();
        builder.append("---").append(groupName).append("---\n");
        for (String command : commandHelps) {
            builder.append("\t").append(command).append("\n");
        }
        return builder.toString();
    }

    /**
     * Fetch command line arguments from source
     * @param interpreter argument source
     * @return not <code>null</code> arguments array
     */
    protected String[] fetchCommandParams(CommandInterpreter interpreter) {
        List<String> result = new ArrayList<String>();
        String param;
        while ((param = interpreter.nextArgument()) != null) {
            result.add(param);
        }
        return result.toArray(new String[result.size()]);
    }

    /**
     * Run command method
     * @param commandName command name
     * @param interpreter interpreter
     */
    protected void runCommand(String commandName, CommandInterpreter interpreter) {
        PrintWriter out = new PrintWriter(new CommandInterpreterWriter(interpreter));
        String[] args = fetchCommandParams(interpreter);
        try {
            Method method = service.getClass().getMethod(commandName, PrintWriter.class, String[].class);
            method.invoke(service, out, args);
        } catch (NoSuchMethodException e) {
            out.println("No such command: " + commandName);
        } catch (Exception e) {
            LOG.log(Level.WARNING, "Unable to execute command: " + commandName + " with args: " + Arrays.toString(args), e);
            e.printStackTrace(out);
        }
    }
}
