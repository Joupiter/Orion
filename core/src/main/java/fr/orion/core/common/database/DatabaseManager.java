package fr.orion.core.common.database;

import fr.orion.api.database.DatabaseLoader;
import fr.orion.api.database.redis.RedisDatabase;
import fr.orion.core.common.database.redis.RedisManager;

public class DatabaseManager implements DatabaseLoader {

    private final RedisManager redisManager;

    public DatabaseManager() {
        this.redisManager = new RedisManager();
    }

    @Override
    public RedisDatabase getRedisDatabase() {
        return redisManager;
    }

    @Override
    public void disconnect() {
        getRedisDatabase().close();
    }

}
