package fr.orion.api.database.redis.pubsub;

import com.google.gson.reflect.TypeToken;
import fr.orion.api.database.redis.RedisPacket;
import lombok.Getter;

@Getter
public abstract class RedisChannel<T extends RedisPacket> {

    private final String name;
    private final Class<T> packet;

    public RedisChannel(String name, Class<T> packet) {
        this.name = name;
        this.packet = packet;
    }

    public abstract RedisListener<T> listener();

    public static <T extends RedisPacket> RedisChannel<T> newChannel(String name, Class<T> packet, RedisListener<T> listener) {
        return new RedisChannel<>(name, packet) {
            @Override
            public RedisListener<T> listener() {
                return listener;
            }
        };
    }

    public TypeToken<T> getTypeToken() {
        return TypeToken.get(getPacket());
    }

}