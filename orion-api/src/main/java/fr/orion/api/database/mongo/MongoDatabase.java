package fr.orion.api.database.mongo;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import fr.orion.api.database.Database;
import fr.orion.api.database.DatabaseCredentials;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public abstract class MongoDatabase implements Database {

    private final DatabaseCredentials credentials;

    private MongoClient client;
    private com.mongodb.reactivestreams.client.MongoDatabase database;

    @Override
    public void connect() {
        this.client = MongoClients.create(getSettings());
        this.database = client.getDatabase(getCredentials().getDatabaseName());
    }

    private MongoClientSettings getSettings() {
        return MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(getCredentials().getUrl()))
                .retryWrites(true).build();
    }

    @Override
    public void disconnect() {
        getClient().close();
    }

}