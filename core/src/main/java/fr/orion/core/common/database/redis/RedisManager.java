package fr.orion.core.common.database.redis;

import fr.orion.api.database.DatabaseCredentialsBuilder;
import fr.orion.api.database.redis.RedisDatabase;

public class RedisManager extends RedisDatabase {

    public RedisManager() {
        super(new DatabaseCredentialsBuilder().setHost("127.0.0.1").setPort(6379).build());
    }

}
