package org.mdev.revolution.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.resource.transaction.backend.jta.internal.JtaTransactionCoordinatorBuilderImpl;
import org.hibernate.service.ServiceRegistry;
import org.mdev.revolution.Revolution;
import org.mdev.revolution.communication.packets.outgoing.notifications.HabboBroadcastMessageComposer;

import javax.inject.Inject;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Hashtable;

public class DatabaseManager {
    private static final Logger logger = LogManager.getLogger(DatabaseManager.class);

    private HikariDataSource dataSource;
    private SessionFactory sessionFactory;

    @Inject
    public void initialize() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");
        hikariConfig.setJdbcUrl("jdbc:mysql://localhost:3306/" + Revolution.getConfig().getString("mysql.database") + "?autoReconnect=true&useSSL=false");
        hikariConfig.setUsername(Revolution.getConfig().getString("mysql.user"));
        hikariConfig.setPassword(Revolution.getConfig().getString("mysql.pass"));
        hikariConfig.setMaximumPoolSize(Revolution.getConfig().getInt("mysql.maxconnections"));
        hikariConfig.setDataSourceJNDI("default");
        hikariConfig.addDataSourceProperty("cachePrepStmts", true);
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", 250);
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
        dataSource = new HikariDataSource(hikariConfig);
        try {
            Hashtable<Object, Object> env = new Hashtable<>();
            env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.fscontext.RefFSContextFactory");
            env.put(Context.PROVIDER_URL, "corbaloc::localhost:9810,:localhost:9811");
            Context context = new InitialContext(env);
            context.bind("java:/comp/env/jdbc/default", dataSource);
        }
        catch(NamingException e) {
            logger.error("Error binding the JNDI DataSource to the Context", e);
            System.exit(0);
        }
        Configuration configuration = new Configuration().configure();
        ServiceRegistry serviceRegistry
                = new StandardServiceRegistryBuilder()
                //.applySetting(Environment.CONNECTION_PROVIDER, "com.zaxxer.hikari.hibernate.HikariConnectionProvider")
                //.applySetting(Environment.SESSION_FACTORY_NAME, dataSource.getDataSourceJNDI())
                //.applySetting(Environment.SESSION_FACTORY_NAME_IS_JNDI, "true")
                .applySetting(Environment.TRANSACTION_COORDINATOR_STRATEGY, JtaTransactionCoordinatorBuilderImpl.class)
                .applySetting(Environment.DIALECT, "org.hibernate.dialect." + (Revolution.getConfig().getString("mysql.dialect").equals("innodb") ? "MySQLInnoDBDialect" : "MySQLDialect"))
                .applySetting(Environment.DATASOURCE, dataSource)
                .build();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
    }

    public Session openSession() {
        return sessionFactory.openSession();
    }

    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        }
        catch(SQLException e) {
            logger.error("Hikari DataSource has been closed!", e);
            e.printStackTrace();
            return null;
        }
    }

    public void dispose() {
        sessionFactory.close();
        dataSource.close();
    }
}
