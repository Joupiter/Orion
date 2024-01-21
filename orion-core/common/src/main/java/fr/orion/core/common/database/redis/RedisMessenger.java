package fr.orion.core.common.database.redis;

import fr.orion.api.database.redis.RedisDatabase;
import fr.orion.api.database.redis.pubsub.AbstractRedisMessenger;

public class RedisMessenger extends AbstractRedisMessenger {

    public RedisMessenger(RedisDatabase redisDatabase) {
        super(redisDatabase);
    }

}