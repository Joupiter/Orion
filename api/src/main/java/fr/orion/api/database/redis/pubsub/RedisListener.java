package fr.orion.api.database.redis.pubsub;

import fr.orion.api.database.redis.RedisPacket;

public interface RedisListener<T extends RedisPacket> {

    void listen(T packet);

}