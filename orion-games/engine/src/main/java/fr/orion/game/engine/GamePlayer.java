package fr.orion.game.engine;

import fr.orion.core.spigot.utils.SpigotUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public abstract class GamePlayer {

    private final UUID uuid;
    private boolean spectator;

    public void sendMessage(String message) {
        getPlayer().sendMessage(SpigotUtils.colorize(message));
    }

    public void sendMessage(String... messages) {
        Flux.just(messages).subscribe(this::sendMessage);
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(getUuid());
    }

}