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

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;
import javax.naming.spi.NamingManager;

public class XjbInitialContextFactory implements JndiRegistry, InitialContextFactory {
    private static final Logger logger = LogManager.getLogger(XjbInitialContextFactory.class);

    private static final Map localContexts = new HashMap();
    private static final HashMap globalContext = new HashMap(); // we use HashMap.clone()

    private static String localContextName;

    private static class ContextInvocationHandler implements InvocationHandler {
        private static final String JAVA_COMP_ENV = "java:comp/env";

        private final Map global;
        private final Map local;

        public ContextInvocationHandler(HashMap global, HashMap local) {
            this.global = (Map)global.clone();
            this.local = (Map)local.clone();
        }

        public Object invoke(Object proxy, Method method, Object[] args)
                throws Throwable {
            if ("lookup".equals(method.getName())) {
                final String jndiName = (String) args[0];
                return lookup(jndiName, proxy);
            }
            throw new UnsupportedOperationException("invoke");
        }

        private Object lookup(String jndiName, Object proxy) throws NamingException {
            // special case
            if (JAVA_COMP_ENV.equals(jndiName)) {
                logger.debug("Returning self as java:comp/env subcontext");
                return proxy;
            }
            jndiName = XjbInitialContextFactory.fullyQualified(jndiName);
            Object result = local.get(jndiName);
            if (result == null) {
                result = global.get(jndiName);
            }
            if (result == null) {
                throw new NamingException("Unable to lookup " + jndiName);
            }
            return result;
        }
    }

    /**
     * Install this class as the system-wide initial context factory.
     *
     * @see NamingManager#setInitialContextFactoryBuilder(javax.naming.spi.InitialContextFactoryBuilder)
     */
    public XjbInitialContextFactory() throws NamingException {
        if (!NamingManager.hasInitialContextFactoryBuilder()) {
            NamingManager.setInitialContextFactoryBuilder(new XjbInitialContextFactoryBuilder());
        }
    }

    /**
     * Reset the registry to its initial state
     *
     * You can only install a factory builder once, so to reset the JNDI registry
     * you need to {@link #clear()} it down.
     */
    public static void clear() {
        globalContext.clear();
        localContexts.clear();
        localContextName = null;
    }

    /**
     * Push a new local JNDI context for future initial context lookups
     *
     * @see #getInitialContext(Hashtable)
     */
    public static void setLocalContext(String contextName) {
        localContextName = contextName;
    }

    /**
     * Get the name of the current local context
     */
    public static String getLocalContextName() {
        return localContextName;
    }

    /**
     * Provide an <tt>InitialContextFactory</tt> to use to navigate a JNDI
     * tree.
     *
     * @return a dynamic {@link Proxy} representing the <tt>Context</tt>
     */
    public Context getInitialContext(Hashtable environment) throws NamingException {
        logger.debug("Getting initial context - current local context = " + localContextName);
        return (Context) Proxy.newProxyInstance(
                Context.class.getClassLoader(),
                new Class[]{Context.class},
                new ContextInvocationHandler(globalContext, getLocalContext(localContextName)));
    }

    /**
     * Register an object against a particular JNDI name in global context
     */
    public void register(String jndiName, Object object) {
        globalContext.put(fullyQualified(jndiName), object);
    }

    /**
     * Register an object against a particular JNDI name in local context
     */
    public void register(String contextName, String jndiName, Object object) {
        getLocalContext(contextName).put(fullyQualified(jndiName), object);
    }

    private static HashMap getLocalContext(String contextName) {
        HashMap localContext = (HashMap) localContexts.get(contextName);
        if (localContext == null) {
            localContext = new HashMap();
            localContexts.put(contextName, localContext);
        }
        return localContext;
    }

    private static String fullyQualified(String jndiName) {
        if (jndiName.startsWith("java:comp/env/")) {
            return jndiName;
        }
        else {
            return "java:comp/env/" + jndiName;
        }
    }
}