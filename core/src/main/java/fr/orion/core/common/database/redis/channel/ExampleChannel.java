package fr.orion.core.common.database.redis.channel;

import fr.orion.api.database.redis.pubsub.RedisChannel;
import fr.orion.api.database.redis.pubsub.RedisListener;
import fr.orion.core.common.database.redis.packet.ExamplePacket;

public class ExampleChannel extends RedisChannel<ExamplePacket> {

    public ExampleChannel() {
        super("example", ExamplePacket.class);
    }

    @Override
    public RedisListener<ExamplePacket> listener() {
        return packet -> System.out.println(packet.getField());
    }

}