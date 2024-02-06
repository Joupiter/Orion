package fr.orion.game.engine.event;

import fr.orion.game.engine.GamePlayer;
import fr.orion.game.engine.SimpleGame;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
@AllArgsConstructor
public class GamePlayerJoinEvent<G extends SimpleGame<P, ?>, P extends GamePlayer> extends Event {

    private static final HandlerList handlers = new HandlerList();

    private final G game;
    private final P gamePlayer;

    public Player getPlayer() {
        return getGamePlayer().getPlayer();
    }

    public void sendJoinMessage() {
        getGame().broadcast("&a+ &7%s (%d/%d)",
                getPlayer().getName(), getGame().getPlayers().size(),
                getGame().getSettings().getGameSize().getMaxPlayer());
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}