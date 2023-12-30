package fr.orion.core.common.database.mongo;

import fr.orion.api.database.DatabaseCredentials;
import fr.orion.api.database.mongo.MongoDatabase;

public class MongoManager extends MongoDatabase {

    public MongoManager() {
        super(DatabaseCredentials.builder().setUrl("mongodb://localhost:27017").setDatabaseName("orion").build());
    }

}
