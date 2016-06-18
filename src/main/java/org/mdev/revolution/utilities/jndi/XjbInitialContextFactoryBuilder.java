/*
 * Created on 02-Feb-2004
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for licence details
 */
package org.mdev.revolution.utilities.jndi;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Hashtable;

import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;
import javax.naming.spi.InitialContextFactoryBuilder;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class XjbInitialContextFactoryBuilder implements InitialContextFactoryBuilder {
    private static final Logger logger = LogManager.getLogger(XjbInitialContextFactoryBuilder.class);


    public XjbInitialContextFactoryBuilder() {
        logger.debug("Constructing factory");
    }

    public InitialContextFactory createInitialContextFactory(Hashtable environment) throws NamingException {
        logger.debug("Can we build it, yes we can!");
        return new XjbInitialContextFactory();
    }
}