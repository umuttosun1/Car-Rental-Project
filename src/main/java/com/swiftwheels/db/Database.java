package com.swiftwheels.db;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Single place that knows how to reach PostgreSQL.
 *
 * <p>Settings are resolved in this order (later wins):
 * <ol>
 *   <li>built-in defaults (localhost:5432/swiftwheels, user/password "swiftwheels")</li>
 *   <li>{@code db.properties} next to the application (the .exe folder) or in the working directory</li>
 *   <li>environment variables {@code SWIFTWHEELS_DB_URL}, {@code SWIFTWHEELS_DB_USER}, {@code SWIFTWHEELS_DB_PASSWORD}</li>
 * </ol>
 */
public final class Database {

    public static final String CONFIG_FILE_NAME = "db.properties";

    private static final String DEFAULT_URL = "jdbc:postgresql://localhost:5432/swiftwheels";
    private static final String DEFAULT_USER = "swiftwheels";
    private static final String DEFAULT_PASSWORD = "swiftwheels";

    private static final Properties CONFIG = loadConfig();

    private Database() {
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url(), CONFIG.getProperty("db.user"), CONFIG.getProperty("db.password"));
    }

    public static String url() {
        return CONFIG.getProperty("db.url");
    }

    /** Where a user can put db.properties to override the defaults. */
    public static Path configLocation() {
        return appDirectory().resolve(CONFIG_FILE_NAME);
    }

    /**
     * Folder that contains the launcher. jpackage sets {@code jpackage.app-path} to the .exe path;
     * when running from an IDE or Gradle we fall back to the working directory.
     */
    public static Path appDirectory() {
        String exePath = System.getProperty("jpackage.app-path");
        if (exePath != null) {
            Path parent = Paths.get(exePath).toAbsolutePath().getParent();
            if (parent != null) {
                return parent;
            }
        }
        return Paths.get("").toAbsolutePath();
    }

    private static Properties loadConfig() {
        Properties props = new Properties();
        props.setProperty("db.url", DEFAULT_URL);
        props.setProperty("db.user", DEFAULT_USER);
        props.setProperty("db.password", DEFAULT_PASSWORD);

        for (Path candidate : new Path[]{configLocation(), Paths.get(CONFIG_FILE_NAME).toAbsolutePath()}) {
            if (Files.isRegularFile(candidate)) {
                try (InputStream in = Files.newInputStream(candidate)) {
                    props.load(in);
                    break;
                } catch (IOException e) {
                    System.err.println("Could not read " + candidate + ": " + e.getMessage());
                }
            }
        }

        override(props, "db.url", "SWIFTWHEELS_DB_URL");
        override(props, "db.user", "SWIFTWHEELS_DB_USER");
        override(props, "db.password", "SWIFTWHEELS_DB_PASSWORD");
        return props;
    }

    private static void override(Properties props, String key, String envName) {
        String value = System.getenv(envName);
        if (value != null && !value.isBlank()) {
            props.setProperty(key, value);
        }
    }
}
