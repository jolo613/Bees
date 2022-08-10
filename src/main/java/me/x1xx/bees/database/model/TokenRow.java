package me.x1xx.bees.database.model;

import net.dv8tion.jda.api.entities.Activity;
import org.jdbi.v3.core.enums.EnumByOrdinal;
import org.jdbi.v3.core.mapper.reflect.JdbiConstructor;

import javax.annotation.Nullable;

public class TokenRow {
    private final String token;

    private final String alias;
    private final int id;

    private final Activity.ActivityType activity;
    private final String url;
    private final String topic;

    public String getAlias() {
        return alias;
    }

    public Activity.ActivityType getActivity() {
        return activity;
    }

    public String getUrl() {
        return url;
    }

    public String getTopic() {
        return topic;
    }

    @JdbiConstructor
    public TokenRow(String token, String alias, int id, @Nullable @EnumByOrdinal Activity.ActivityType activity, @Nullable String url, @Nullable String topic) {
        this.token = token;
        this.alias = alias;
        this.id = id;
        this.activity = activity;
        this.url = url;
        this.topic = topic;
    }



    public int getId() {
        return id;
    }

    public String getToken() {
        return token;
    }
}
