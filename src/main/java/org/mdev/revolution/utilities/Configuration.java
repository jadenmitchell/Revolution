package org.mdev.revolution.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.Properties;

public class Configuration {
    private static final Logger logger = LogManager.getLogger(Configuration.class);

    private Properties props;

    public Configuration(String name) {
        try {
            props = new Properties();
            props.load(new FileReader(new File(name)));
        } catch (FileNotFoundException e) {
            logger.error("Unable to find the configuration file: " + name, e);
            e.printStackTrace();
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public int size() {
        return props.size();
    }

    public String getOrDefault(String key, String value) {
        return props.getOrDefault(key, value).toString();
    }

    public String getString(String key) {
        return props.getProperty(key);
    }

    public int getInt(String key) {
        return Integer.parseInt(getString(key));
    }
}
