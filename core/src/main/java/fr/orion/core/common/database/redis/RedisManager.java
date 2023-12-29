package fr.orion.core.common.database.redis;

import fr.orion.api.database.DatabaseCredentials;
import fr.orion.api.database.redis.RedisDatabase;

public class RedisManager extends RedisDatabase {

    public RedisManager() {
        super(DatabaseCredentials.builder().setHost("127.0.0.1").setPort(6379).build());
    }

}
