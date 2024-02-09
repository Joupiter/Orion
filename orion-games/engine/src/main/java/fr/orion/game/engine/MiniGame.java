package fr.orion.game.engine;

import org.bukkit.entity.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public interface MiniGame {

    Logger log = LoggerFactory.getLogger(MiniGame.class);

    void load();

    void unload();

    void joinGame(Player player, boolean spectator);

    default void joinGame(Player player) {
        joinGame(player, true);
    }

    void leaveGame(UUID uuid);

    void endGame();

    default void debug(String message, Object ... arguments) {
        log.debug(message, arguments);
    }

    void sendDebugInfoMessage(Player player);

}