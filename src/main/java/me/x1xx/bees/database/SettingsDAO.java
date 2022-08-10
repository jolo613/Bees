package me.x1xx.bees.database;

import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface SettingsDAO {
    @SqlUpdate("CREATE TABLE IF NOT EXISTS 'settings' ('guild_id' TEXT PRIMARY KEY NOT NULL, 'prefix' TEXT);")
    void createTable();
}
