package fr.orion.api.database.redis;

import fr.orion.api.database.Database;
import fr.orion.api.database.DatabaseCredentials;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisStringAsyncCommands;
import io.lettuce.core.api.reactive.RedisStringReactiveCommands;
import lombok.Getter;

import java.io.Closeable;

@Getter
public abstract class RedisDatabase implements Database {

    private final RedisClient client;

    private StatefulRedisConnection<String, String> connection;

    private RedisStringAsyncCommands<String, String> asyncCommands;
    private RedisStringReactiveCommands<String, String> reactiveCommands;

    public RedisDatabase(DatabaseCredentials credentials) {
        this.client = RedisClient.create("redis://" + credentials.getHost() + ":" + credentials.getPort() + "/");
    }

    @Override
    public void connect() {
        this.connection = getClient().connect();
        this.asyncCommands = getConnection().async();
        this.reactiveCommands = getConnection().reactive();
    }

    @Override
    public void disconnect() {
        getConnection().close();
        getClient().close();
    }

}
