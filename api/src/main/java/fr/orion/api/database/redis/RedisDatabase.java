package fr.orion.api.database.redis;

import fr.orion.api.database.Database;
import fr.orion.api.database.DatabaseCredentials;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.api.reactive.RedisReactiveCommands;
import lombok.Getter;

@Getter
public abstract class RedisDatabase implements Database {

    private final DatabaseCredentials credentials;
    private final RedisClient client;

    private StatefulRedisConnection<String, String> connection;

    private RedisAsyncCommands<String, String> asyncCommands;
    private RedisReactiveCommands<String, String> reactiveCommands;

    public RedisDatabase(DatabaseCredentials credentials) {
        this.credentials = credentials;
        this.client = getNewClient();
    }

    @Override
    public void connect() {
        this.connection = getClient().connect();
        this.asyncCommands = getConnection().async();
        this.reactiveCommands = getConnection().reactive();
    }

    public RedisClient getNewClient() {
        return RedisClient.create("redis://" + getCredentials().getHost() + ":" + getCredentials().getPort() + "/");
    }

    @Override
    public void disconnect() {
        getConnection().close();
        getClient().close();
    }

}
