package fr.orion.game.engine.event;

import fr.orion.game.engine.GamePlayer;
import fr.orion.game.engine.SimpleGame;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.function.Predicate;

@Getter
@AllArgsConstructor
public class GamePlayerLeaveEvent<G extends SimpleGame<P, ?>, P extends GamePlayer> extends Event {

    private static final HandlerList handlers = new HandlerList();

    private final G game;
    private final P gamePlayer;

    public Player getPlayer() {
        return getGamePlayer().getPlayer();
    }

    public void sendLeaveMessage() {
        getGame().broadcast(broadcastFilter().negate(), "&c- &7%s (%d/%d)",
                getPlayer().getName(),
                getGame().getPlayers().size() - 1,
                getGame().getSettings().getGameSize().getMaxPlayer());
    }

    private Predicate<P> broadcastFilter() {
        return getGamePlayer()::equals;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}