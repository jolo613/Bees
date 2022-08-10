package me.x1xx.bees;


import me.x1xx.bees.configuration.Configuration;
import me.x1xx.bees.database.SettingsDAO;
import me.x1xx.bees.database.TokenDAO;
import me.x1xx.bees.modules.ModuleInitializer;
import me.x1xx.bees.utility.FileWatcher;
import me.x1xx.bees.utility.WrappedObject;
import me.x1xx.bees.utility.configuration.Config;
import me.x1xx.bees.utility.configuration.ConfigManager;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class Main {
    private final Logger logger = LoggerFactory.getLogger(Main.class);
    private final ModuleInitializer initializer;

    public Jdbi getDatabase() {
        return database;
    }

    public ModuleInitializer getInitializer() {
        return initializer;
    }

    private final Config<Configuration> config;

    private final Jdbi database;


    public Logger getLogger() {
        return logger;
    }

    public Config<Configuration> getConfigObject() {
        return config;
    }

    public Configuration getConfig() {
        return config.getConfiguration();
    }

    public void reloadEvent() {
        getConfigObject().reloadConfig();
        getLogger().info("Configuration file has been reloaded.");
    }

    public Main() {

        config = new ConfigManager(Main.class).loadConfig("/data/config.json", new Configuration());

        getLogger().info("Configuration file has been loaded.");

        FileWatcher<WrappedObject<Config<Configuration>, Main>> fileWatcher = new FileWatcher<>(config.getConfigurationFile(),
                (config) -> {
                    config.getSecond().logger.info("File change detected, configuration file changed, reloading...");
                    config.getFirst().reloadConfig();
                    config.getSecond().reloadEvent();


                }, new WrappedObject<>(config, this));

        fileWatcher.start();

        getLogger().info("Watching configuration changes.");

        String path = new File(new File(this.getClass().getProtectionDomain()
                .getCodeSource().getLocation().getPath()).getParent(), "/data/database.db").getPath();

        getLogger().info("Connecting to database");

        database = Jdbi.create("jdbc:sqlite:" + path);
        database.installPlugin(new SqlObjectPlugin());


        database.useExtension(TokenDAO.class, TokenDAO::createTable);
        database.useExtension(SettingsDAO.class, SettingsDAO::createTable);

        getLogger().info("Connected to database");

        getLogger().info("Starting discord client instances");


        initializer = new ModuleInitializer(this);

        getLogger().info("Instances successfully started");


    }


    public void shutDown() {
        initializer.shutdown();
        config.saveConfig();
        logger.info("Shutting down!");
        System.exit(0);
    }

    public static void main(String[] args) {
        Main main = new Main();

        CommandScanner.start(main);
    }
}
