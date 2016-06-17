package org.mdev.revolution.database;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import org.mdev.revolution.Revolution;

import javax.activation.DataSource;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseManager {
    private static final Logger logger = LogManager.getLogger(DatabaseManager.class);

    private HikariDataSource dataSource;
    private SessionFactory sessionFactory;
    private EntityManagerFactory emf;
    private Injector injector;

    @Inject
    public void initialize() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");
        hikariConfig.setDataSourceJNDI("jdbc/HikariDataSource");
        hikariConfig.setJdbcUrl("jdbc:mysql://localhost:3306/" + Revolution.getConfig().getString("mysql.database") + "?autoReconnect=true&useSSL=false");
        hikariConfig.setUsername(Revolution.getConfig().getString("mysql.user"));
        hikariConfig.setPassword(Revolution.getConfig().getString("mysql.pass"));
        hikariConfig.setMaximumPoolSize(Revolution.getConfig().getInt("mysql.maxconnections"));
        hikariConfig.addDataSourceProperty("cachePrepStmts", true);
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", 250);
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
        dataSource = new HikariDataSource(hikariConfig);
        Configuration configuration = new Configuration().configure();
        ServiceRegistry serviceRegistry
                = new StandardServiceRegistryBuilder()
                //.applySetting(Environment.TRANSACTION_COORDINATOR_STRATEGY, JtaTransactionCoordinatorBuilderImpl.class)
                //.applySetting(Environment.CONNECTION_PROVIDER, "com.zaxxer.hikari.hibernate.HikariConnectionProvider")
                //.applySetting(Environment.SESSION_FACTORY_NAME, dataSource.getDataSourceJNDI())
                //.applySetting(Environment.SESSION_FACTORY_NAME_IS_JNDI, "true")
                .applySetting(Environment.DIALECT, "org.hibernate.dialect." + (Revolution.getConfig().getString("mysql.dialect").equals("innodb") ? "MySQLInnoDBDialect" : "MySQLDialect"))
                .applySetting(Environment.DATASOURCE, dataSource)
                .build();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        emf = Persistence.createEntityManagerFactory("org.mdev.revolution.jpa");
        injector = Guice.createInjector(new JpaPersistModule("org.mdev.revolution.jpa"));
    }

    public EntityManager createEntityManager() {
        return emf.createEntityManager();
    }

    @SuppressWarnings("unchecked")
    public Injector getInjector() {
        return injector;
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
