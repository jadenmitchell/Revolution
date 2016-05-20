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

import javax.activation.DataSource;
import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
    private static final Logger logger = LogManager.getLogger(DatabaseManager.class);

    private HikariDataSource dataSource;
    private SessionFactory sessionFactory;

    @Inject
    public void initialize() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");
        hikariConfig.setJdbcUrl("jdbc:mysql://localhost:3306/" + Revolution.getConfig().getString("mysql.database") + "?autoReconnect=true&useSSL=false");
        //System.out.println(Revolution.getConfig().getString("mysql.user") + "\n" + Revolution.getConfig().getString("mysql.pass"));
        hikariConfig.setUsername(Revolution.getConfig().getString("mysql.user"));
        hikariConfig.setPassword(Revolution.getConfig().getString("mysql.pass"));
        hikariConfig.setMaximumPoolSize(Revolution.getConfig().getInt("mysql.maxconnections"));
        hikariConfig.addDataSourceProperty("cachePrepStmts", true);
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", 250);
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
        setDataSource(new HikariDataSource(hikariConfig));
        Configuration configuration = new Configuration().configure();
        ServiceRegistry serviceRegistry
                = new StandardServiceRegistryBuilder()
                .applySetting(Environment.TRANSACTION_COORDINATOR_STRATEGY, JtaTransactionCoordinatorBuilderImpl.class)
                //.applySetting(Environment.CONNECTION_PROVIDER, "com.zaxxer.hikari.hibernate.HikariConnectionProvider")
                .applySetting(Environment.DATASOURCE, dataSource)
                .build();
        setSessionFactory(configuration.buildSessionFactory(serviceRegistry));
    }

    public Session openSession() {
        return sessionFactory.openSession();
    }

    @SuppressWarnings("unused")
    public PreparedStatement query(String sql) {
        Connection connection = getConnection();

        try {
            return connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        } catch (SQLException e) {
            logger.error("Failed to execute query: " + sql, e);
            e.printStackTrace();
            System.exit(0);
        }

        // This should never happen, LORD HAVE MERCY!!!!
        logger.info("What the fuck just happened???");
        return null;
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

    @SuppressWarnings("unused")
    public DataSource getDataSource() {
        return (DataSource)dataSource;
    }

    @SuppressWarnings("unused")
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setDataSource(HikariDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void dispose() {
        sessionFactory.close();
        dataSource.close();
    }
}
