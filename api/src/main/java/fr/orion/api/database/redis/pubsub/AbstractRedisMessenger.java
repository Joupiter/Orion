package fr.orion.api.database.redis.pubsub;

import fr.orion.api.OrionApi;
import fr.orion.api.database.redis.RedisDatabase;
import fr.orion.api.database.redis.RedisPacket;
import fr.orion.api.utils.Utils;
import ga.windpvp.windspigot.commons.GsonUtils;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import io.lettuce.core.pubsub.api.reactive.ChannelMessage;
import io.lettuce.core.pubsub.api.reactive.RedisPubSubReactiveCommands;
import lombok.Getter;
import reactor.core.scheduler.Schedulers;

import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

@Getter
public abstract class AbstractRedisMessenger {

    private final StatefulRedisPubSubConnection<String, String> connection;
    private final RedisPubSubReactiveCommands<String, String> reactivePubSub;

    private final Set<RedisChannel<?>> channels;

    public AbstractRedisMessenger(RedisDatabase redisDatabase) {
        this.connection = redisDatabase.getClient().connectPubSub();
        this.reactivePubSub = connection.reactive();
        this.channels = new HashSet<>();
    }

    public void connect() {
        Utils.ifFalse(getChannelNamesList().isEmpty(), () -> getReactivePubSub().subscribe(getChannelNames()).subscribe());
        getReactivePubSub().observeChannels().doOnNext(this::onReceive).subscribeOn(Schedulers.boundedElastic()).subscribe();
    }

    public void disconnect() {
        getReactivePubSub().unsubscribe(getChannelNames());
        getChannels().clear();
    }

    private void onReceive(ChannelMessage<String, String> channelMessage) {
        getChannel(channelMessage.getChannel())
                .ifPresent(channel -> channel.listener().listen(fromJson(channelMessage, channel.getTypeToken().getType())));
    }

    public void addChannel(RedisChannel<?> channel) {
        getChannels().add(channel);
        getReactivePubSub().subscribe(channel.getName()).subscribe();
    }

    public void addChannels(RedisChannel<?>... channels) {
        Arrays.asList(channels).forEach(this::addChannel);
    }

    public Optional<RedisChannel<?>> getChannel(String name) {
        return getChannels().stream().filter(channel -> channel.getName().equals(name)).findFirst();
    }

    public <T extends RedisPacket> Optional<RedisChannel<?>> getChannel(Class<T> clazz) {
        return getChannels().stream().filter(channel -> channel.getPacket().equals(clazz)).findFirst();
    }

    public void publish(String channel, RedisPacket packet) {
        OrionApi.getProvider().getDatabaseLoader().getRedisDatabase().getReactiveCommands().publish(channel, toJson(packet)).subscribeOn(Schedulers.boundedElastic()).subscribe();
    }

    private <T extends RedisPacket> T fromJson(ChannelMessage<String, String> channelMessage, Type type) {
        return GsonUtils.getGson().fromJson(channelMessage.getMessage(), type);
    }

    private String toJson(RedisPacket redisPacket) {
        return GsonUtils.getGson().toJson(redisPacket);
    }

    private List<String> getChannelNamesList() {
        return getChannels().stream().map(RedisChannel::getName).collect(Collectors.toList());
    }

    private String[] getChannelNames() {
        return getChannels().stream().map(RedisChannel::getName).toArray(String[]::new);
    }

}