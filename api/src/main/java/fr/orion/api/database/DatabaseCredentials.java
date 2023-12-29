package fr.orion.api.database;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DatabaseCredentials {

    private String url, host, username, password, databaseName;
    private int port;

    public DatabaseCredentials(String url, String host, String username, String password, String databaseName, int port) {
        this.url = url;
        this.host = host;
        this.username = username;
        this.password = password;
        this.databaseName = databaseName;
        this.port = port;
    }

    public static DatabaseCredentialsBuilder builder() {
        return new DatabaseCredentialsBuilder();
    }

    public static class DatabaseCredentialsBuilder {

        private final DatabaseCredentials credentials;

        public DatabaseCredentialsBuilder() {
            this.credentials = new DatabaseCredentials();
        }

        public DatabaseCredentialsBuilder setUrl(String url) {
            credentials.setUrl(url);
            return this;
        }

        public DatabaseCredentialsBuilder setHost(String host) {
            credentials.setHost(host);
            return this;
        }

        public DatabaseCredentialsBuilder setUsername(String username) {
            credentials.setUsername(username);
            return this;
        }

        public DatabaseCredentialsBuilder setPassword(String password) {
            credentials.setPassword(password);
            return this;
        }

        public DatabaseCredentialsBuilder setDatabaseName(String databaseName) {
            credentials.setDatabaseName(databaseName);
            return this;
        }

        public DatabaseCredentialsBuilder setPort(int port) {
            credentials.setPort(port);
            return this;
        }

        public DatabaseCredentials build() {
            return credentials;
        }

    }

}