package fr.orion.core.spigot.common.channel;

import fr.orion.api.database.redis.pubsub.RedisChannel;
import fr.orion.api.database.redis.pubsub.RedisListener;
import fr.orion.core.common.database.redis.packet.FinePacket;

public class FineChannel extends RedisChannel<FinePacket> {

    public FineChannel() {
        super("fine", FinePacket.class);
    }

    @Override
    public RedisListener<FinePacket> listener() {
        return packet -> System.out.println(packet.getUser().getUuid().toString() + ": " + packet.getUser().getCoins());
    }

}
