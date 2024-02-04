package fr.orion.game.engine.phase;

import fr.orion.game.engine.Game;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.UUID;

@Getter
public abstract class AbstractGamePhase<G extends Game<?, ?, ?>> implements GamePhase {

    private final G game;

    public AbstractGamePhase(G game) {
        this.game = game;
    }

    public void startPhase() {
        onStart();
    }

    public void endPhase() {
        onEnd();
        unregister();
        getGame().getPhaseManager().tryAdvance(this);
    }

    public void cancelPhase() {
        onCancel();
        unregister();
        getGame().getPhaseManager().tryRetreat(this);
    }

    public void unregister() {
        // TODO: clear game event bus listeners
    }

    /*public <EventType extends Event> void registerEvent(Class<EventType> eventClass, Consumer<EventType> consumer) {
        registerEvent(eventClass, null, consumer);
    }

    public <EventType extends Event> void registerEvent(Class<EventType> eventClass, Function<EventType, Player> function, Consumer<EventType> consumer) {
        EventListenerWrapper<EventType> wrapper = new EventListenerWrapper<>(consumer);

        Bukkit.getPluginManager().registerEvent(eventClass, wrapper, EventPriority.NORMAL, eventExecutor(function, consumer), getGame().getPlugin());
        getEvents().add(wrapper);
    }

    private <EventType extends Event> EventExecutor eventExecutor(Function<EventType, Player> function, Consumer<EventType> consumer) {
        return (listener, event) ->
                Utils.ifPresentOrElse(Optional.ofNullable(function.apply((EventType) event)).filter(this::canTriggerEvent),
                        uuid -> consumer.accept((EventType) event),
                        () -> consumer.accept((EventType) event));
    }*/

    public boolean canTriggerEvent(UUID uuid) {
        return getGame().containsPlayer(uuid);
    }

    public boolean canTriggerEvent(Player player) {
        return getGame().containsPlayer(player.getUniqueId());
    }

}