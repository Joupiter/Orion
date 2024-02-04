package fr.orion.game.engine.event;

import fr.orion.game.engine.Game;
import fr.orion.game.engine.GamePlayer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.function.Predicate;

@Getter
@AllArgsConstructor
public class GamePlayerLeaveEvent<G extends GamePlayer> extends Event {

    private static final HandlerList handlers = new HandlerList();

    private final Game<G, ?, ?> game;
    private final G gamePlayer;

    public Player getPlayer() {
        return getGamePlayer().getPlayer();
    }

    public void sendLeaveMessage() {
        getGame().broadcast(broadcastFilter().negate(), "&c- &7%s (%d/%d)", getPlayer().getName(), getGame().getPlayers().size() - 1, getGame().getSettings().getGameSize().getMaxPlayer());
    }

    private Predicate<G> broadcastFilter() {
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