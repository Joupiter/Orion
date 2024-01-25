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

    @Getter(value = AccessLevel.PRIVATE)
    public static class DatabaseCredentialsBuilder {

        private final DatabaseCredentials credentials;

        public DatabaseCredentialsBuilder() {
            this.credentials = new DatabaseCredentials();
        }

        public DatabaseCredentialsBuilder setUrl(String url) {
            getCredentials().setUrl(url);
            return this;
        }

        public DatabaseCredentialsBuilder setHost(String host) {
            getCredentials().setHost(host);
            return this;
        }

        public DatabaseCredentialsBuilder setUsername(String username) {
            getCredentials().setUsername(username);
            return this;
        }

        public DatabaseCredentialsBuilder setPassword(String password) {
            getCredentials().setPassword(password);
            return this;
        }

        public DatabaseCredentialsBuilder setDatabaseName(String databaseName) {
            getCredentials().setDatabaseName(databaseName);
            return this;
        }

        public DatabaseCredentialsBuilder setPort(int port) {
            getCredentials().setPort(port);
            return this;
        }

        public DatabaseCredentials build() {
            return getCredentials();
        }

    }

}