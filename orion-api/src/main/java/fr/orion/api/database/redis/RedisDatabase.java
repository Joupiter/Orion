package fr.orion.api.database.redis;

import fr.orion.api.database.Database;
import fr.orion.api.database.DatabaseCredentials;
import fr.orion.api.utils.GsonUtils;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.api.reactive.RedisReactiveCommands;
import io.lettuce.core.api.sync.RedisCommands;
import lombok.Getter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;

@Getter
public abstract class RedisDatabase implements Database {

    private final DatabaseCredentials credentials;
    private final RedisClient client;

    private StatefulRedisConnection<String, String> connection;

    private RedisCommands<String, String> syncCommands;
    private RedisAsyncCommands<String, String> asyncCommands;
    private RedisReactiveCommands<String, String> reactiveCommands;

    public RedisDatabase(DatabaseCredentials credentials) {
        this.credentials = credentials;
        this.client = RedisClient.create("redis://" + getCredentials().getHost() + ":" + getCredentials().getPort() + "/");
    }

    @Override
    public void connect() {
        this.connection = getClient().connect();
        this.syncCommands = getConnection().sync();
        this.asyncCommands = getConnection().async();
        this.reactiveCommands = getConnection().reactive();
    }

    public <T> Mono<T> get(String key, Class<T> clazz) {
        return getReactiveCommands().get(key).map(value -> GsonUtils.getGson().fromJson(value, clazz));
    }

    public Mono<String> get(String key) {
        return getReactiveCommands().get(key);
    }

    public <T> Mono<String> set(String key, T value) {
        return set(key, GsonUtils.getGson().toJson(value));
    }

    public Mono<String> set(String key, String value) {
        return getReactiveCommands().set(key, value);
    }

    public Mono<Long> del(String... keys) {
        return getReactiveCommands().del(keys);
    }

    public Flux<String> keys(String pattern) {
        return getReactiveCommands().keys(pattern);
    }

    public Mono<Long> exist(String... keys) {
        return getReactiveCommands().exists(keys);
    }

    public void transaction(Consumer<RedisReactiveCommands<String, String>> consumer) {
        getReactiveCommands().multi().subscribe(s -> {
            consumer.accept(getReactiveCommands());
            getReactiveCommands().exec().subscribe();
        });
    }

    @Override
    public void disconnect() {
        getConnection().close();
        getClient().close();
    }

}