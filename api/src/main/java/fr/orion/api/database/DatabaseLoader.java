package fr.orion.api.database;

import fr.orion.api.database.mongo.MongoDatabase;
import fr.orion.api.database.redis.RedisDatabase;
import fr.orion.api.database.redis.pubsub.AbstractRedisMessenger;

public interface DatabaseLoader {

    default MongoDatabase getMongoDatabase() {
        return null;
    };

    RedisDatabase getRedisDatabase();

    AbstractRedisMessenger getRedisMessenger();

    void connect();

    void disconnect();

}
