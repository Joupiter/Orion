package fr.orion.api.database;

import fr.orion.api.database.mongo.MongoDatabase;
import fr.orion.api.database.redis.RedisDatabase;
import org.apache.commons.lang.NotImplementedException;

public interface DatabaseLoader {

    default MongoDatabase getMongoDatabase() {
        return null;
    };

    RedisDatabase getRedisDatabase();

    void connect();

    void disconnect();

}
