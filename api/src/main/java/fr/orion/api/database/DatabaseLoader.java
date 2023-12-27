package fr.orion.api.database;

import fr.orion.api.database.redis.RedisDatabase;

public interface DatabaseLoader {

    RedisDatabase getRedisDatabase();

    void disconnect();

}
