package fr.orion.api.user;

import fr.orion.api.server.Server;

import java.util.Arrays;

public interface Session {

    Server getServer();

    default String getServerName() {
        return getServer().getName();
    }

    void sendMessage(String message);

    default void sendMessages(String... messages) {
        Arrays.asList(messages).forEach(this::sendMessage);
    }

}