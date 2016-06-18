/*
 * Created on 19-Mar-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package org.mdev.revolution.utilities.jndi;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public interface JndiRegistry {
    /**
     * Register an object against a particular JNDI name in global context
     */
    void register(String jndiName, Object object);

    /**
     * Register an object against a particular JNDI name in local context
     */
    void register(String localContextName, String jndiName, Object object);
}