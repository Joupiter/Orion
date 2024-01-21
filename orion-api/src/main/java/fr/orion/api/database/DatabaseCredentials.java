package fr.orion.api.database;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DatabaseCredentials {

    private String url, host, username, password, databaseName;
    private int port;

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