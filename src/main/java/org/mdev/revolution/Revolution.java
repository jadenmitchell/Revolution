package org.mdev.revolution;

import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.mycila.guice.ext.closeable.CloseableModule;
import com.mycila.guice.ext.jsr250.Jsr250Module;
import com.netflix.governator.configuration.ArchaiusConfigurationProvider;
import com.netflix.governator.guice.LifecycleInjector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mdev.revolution.communication.packets.PacketManager;
import org.mdev.revolution.database.DatabaseManager;
import org.mdev.revolution.game.Game;
import org.mdev.revolution.network.Server;
import org.mdev.revolution.network.sessions.SessionManager;
import org.mdev.revolution.threading.ThreadPool;
import org.mdev.revolution.utilities.Configuration;

import java.io.File;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.Executors;

@Singleton
public class Revolution {
    private static final Logger logger = LogManager.getLogger(Revolution.class);

    public static final String TARGET_CLIENT = "PRODUCTION-201601012205-226667486";
    public static final String N = "85F5D787695551F8899858DD52C1C19D080308C78C13FD9FBDCF6B8852B6FEF8A9380F5DEB39CC64F65321F94FE415F02E455A9A82B7E55AC3E9DF684347AA04A4A95B798A9C465042CB8EC95C91F0B68415E1A8CD9BCE1473D1397319295E0C7AA362E25992D83289FD4E2DAB39F794D4D779671DF18A898BFDCFE25CD0A5F1";
    public static final String D = "594E8FAF9B8E36A5B1103B3E372BD668B00205DA5D62A9152934F25AE1CF54A5C6255F93F22688434EE216A63542B94AC98391BC57254391D7F13F9AD7851C021703B238CD44EE121992AD950C020B899764A5FDDF9F09D459887AAA26BAAC08450FA6490243CAE1D7E69F372B6CAFE4C5BA0FBC095C9537E33EA795E6A848A3";
    public static final String E = "3";

    private static Injector injector;
    private static Configuration config;
    private static Revolution instance;
    private static ThreadPool globalThreadPool;

    public static Revolution getInstance() {
        if (instance == null) {
            synchronized (Revolution.class) {
                if (instance == null) {
                    instance = injector.getInstance(Revolution.class);
                }
            }
        }
        return instance;
    }

    public static Configuration getConfig() {
        return config;
    }

    public static ThreadPool getGlobalThreadPool() {
        return globalThreadPool;
    }

    private Server server;
    private Game game;
    private DatabaseManager databaseManager;
    private SessionManager sessionManager;
    private PacketManager packetManager;

    private static void loadConfiguration(String configFile) {
        if (!new File(configFile).exists()) {
            logger.error("Unable to find the configuration file.");
            System.exit(0); // Fatal error!
        }
        config = new Configuration(configFile);
        logger.info("Configuration successfully loaded " + getConfig().size() + " properties.");
    }

    private static void loadEverythingElse() {
        Thread.currentThread().setPriority(Thread.MAX_PRIORITY);

        globalThreadPool = new ThreadPool(Executors.newFixedThreadPool(3));
        injector = LifecycleInjector
                .builder()
                .usingBasePackages("org.mdev.revolution")
                .withBootstrapModule(
                        binder -> binder.bindConfigurationProvider().toInstance(ArchaiusConfigurationProvider.builder().build())
                )
                .withModuleClass(CloseableModule.class)
                .withModuleClass(Jsr250Module.class)
                .build().createInjector();

        getInstance().databaseManager = new DatabaseManager();
        getInstance().databaseManager.initialize();

        getInstance().packetManager = new PacketManager();
        getInstance().packetManager.initialize();

        getInstance().sessionManager = new SessionManager();
        getInstance().game = new Game();

        getInstance().getServer().start();
        Thread.currentThread().setPriority(Thread.NORM_PRIORITY);
    }

    public Server getServer() {
        if (server == null) {
            synchronized (Server.class) {
                if (server == null) {
                    server = injector.getInstance(Server.class);
                }
            }
        }
        return server;
    }

    public Game getGame() {
        return game;
    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    public SessionManager getSessionManager() {
        return sessionManager;
    }

    public PacketManager getPacketManager() {
        return packetManager;
    }

    public static Injector getInjector() {
        return injector;
    }

    public static void main(String[] args) {
        String configFile = "./config/revolution.properties";

        if (args.length > 0) {
            switch (args[0].toLowerCase().trim()) {
                case "c":
                    configFile = args[1];
                    break;
                default:
                    System.out.println("Unsupported command-line argument, please make sure you've spelled correctly as everything is case-sensitive.");
                    System.out.println("Press any key to quit.");
                    System.exit(0);
                    break;
            }
        }

        long start = System.currentTimeMillis();

        Properties props = System.getProperties();
        System.err.println("-=-=-=-=-=-=-=-");
        System.err.println("Computer Information");
        System.err.println("\tOperating System: " + props.getProperty("os.name"));
        System.err.println("\tOperating System Architecture: " + props.getProperty("os.arch"));
        System.err.println("\tProcessors Available: " + Runtime.getRuntime().availableProcessors());

        System.err.println("\n-=-=-=-=-=-=-=-");
        System.err.println("Virtual Machine Information");
        System.err.println("\tVirtual Machine Vendor: " + props.getProperty("java.vendor"));
        System.err.println("\tVirtual Machine Version: " + props.getProperty("java.version"));

        loadConfiguration(configFile);
        loadEverythingElse();

        long elapsed = System.currentTimeMillis() - start;
        logger.info("Revolution Server started up in " + elapsed + "ms");

        Thread hook = new Thread(Revolution::shutdown);
        Runtime.getRuntime().addShutdownHook(hook);

        while (true) {}
    }

    private static void shutdown() {
        globalThreadPool.dispose();
        //Revolution.getInstance().getGame().dispose();
        Revolution.getInstance().getPacketManager().dispose();
        Revolution.getInstance().getDatabaseManager().dispose();
        Revolution.getInstance().getServer().stop();
    }

    public static String dateToUnixTimestamp(Date fecha) {
        String res = "";
        Date aux = stringToDate("1970-01-01 00:00:00");
        Timestamp aux1 = dateToTimeStamp(aux);
        Timestamp aux2 = dateToTimeStamp(fecha);
        long diferenciaMils = aux2.getTime() - aux1.getTime();
        long segundos = diferenciaMils / 1000;
        return res + segundos;
    }

    public static Date stringToDate(String fecha) {
        // Solo funciona con string en el formato yyyy-MM-dd HH:mm:ss
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date res = null;
        try {
            res = format.parse(fecha);
        } catch (ParseException e) {
            logger.error("Failed to convert string to date", e);
        }
        return res;
    }

    public static Timestamp dateToTimeStamp(Date fecha) {
        return new Timestamp(fecha.getTime());
    }

    public static Date fechaHoraSistema() {
        return new Date(System.currentTimeMillis());
    }

    public static int getUnixTimestamp() {
        return Integer.parseInt(dateToUnixTimestamp(fechaHoraSistema()));
    }
}