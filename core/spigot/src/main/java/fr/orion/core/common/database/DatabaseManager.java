package fr.orion.core.common.database;

import fr.orion.api.database.DatabaseLoader;
import fr.orion.api.database.mongo.MongoDatabase;
import fr.orion.api.database.redis.RedisDatabase;
import fr.orion.api.database.redis.pubsub.AbstractRedisMessenger;
import fr.orion.core.common.database.mongo.MongoManager;
import fr.orion.core.common.database.redis.RedisManager;
import fr.orion.core.common.database.redis.RedisMessenger;

public class DatabaseManager implements DatabaseLoader {

    private final MongoManager mongoManager;
    private final RedisManager redisManager;

    private final AbstractRedisMessenger redisMessenger;

    public DatabaseManager() {
        this.mongoManager = new MongoManager();
        this.redisManager = new RedisManager();
        this.redisMessenger = new RedisMessenger(redisManager);
    }

    @Override
    public MongoDatabase getMongoDatabase() {
        return mongoManager;
    }

    @Override
    public RedisDatabase getRedisDatabase() {
        return redisManager;
    }

    @Override
    public AbstractRedisMessenger getRedisMessenger() {
        return redisMessenger;
    }

    @Override
    public void connect() {
        getMongoDatabase().connect();
        getRedisDatabase().connect();
        getRedisMessenger().connect();
    }

    @Override
    public void disconnect() {
        getRedisMessenger().disconnect();
        getRedisDatabase().disconnect();
        getMongoDatabase().disconnect();
    }

}
