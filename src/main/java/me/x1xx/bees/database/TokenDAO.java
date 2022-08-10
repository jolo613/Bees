package me.x1xx.bees.database;

import me.x1xx.bees.database.model.TokenRow;
import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

public interface TokenDAO {
    @SqlUpdate("CREATE TABLE IF NOT EXISTS `tokens` (`id` INTEGER PRIMARY KEY AUTOINCREMENT,`token` TEXT NOT NULL, 'alias' TEXT NOT NULL, 'activity' INTEGER, 'url' TEXT, 'topic' TEXT);")
    void createTable();

    @SqlUpdate("INSERT INTO 'tokens' ('token', 'alias') VALUES (:token, :alias);")
    @GetGeneratedKeys({"id"})
    int insert(String token, String alias);

    @SqlQuery("SELECT * FROM 'tokens';")
    @RegisterConstructorMapper(TokenRow.class)
    List<TokenRow> retrieveAll();

    @SqlQuery("SELECT * FROM 'tokens' WHERE \"id\"= :id;")
    @RegisterConstructorMapper(TokenRow.class)
    TokenRow retrieveById(int id);


    @SqlUpdate("DELETE FROM 'tokens' WHERE \"id\"= :id;")
    void removeById(int id);

    @SqlUpdate("UPDATE 'tokens' SET 'topic' = :topic WHERE \"id\" = :id;")
    void updateTopic(int id, String topic);

    @SqlUpdate("UPDATE 'tokens' SET 'topic' = :topic WHERE \"id\" = :id;")
    void updateURL(int id, String url);

    @SqlUpdate("UPDATE 'tokens' SET 'activity' = :activity WHERE \"id\" = :id;")
    void updateActivity(int id, int activity);
}
