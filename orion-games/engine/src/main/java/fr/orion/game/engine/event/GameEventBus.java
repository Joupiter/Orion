package fr.orion.game.engine.event;

import fr.orion.core.spigot.utils.event.EventBus;
import fr.orion.core.spigot.utils.event.EventSettings;
import org.bukkit.event.Event;

import java.util.function.Consumer;

public class GameEventBus implements EventBus {

    /*

    Need du réfléchissement pour ça mdr

    Game -> List<GameEventBus>
    PhaseManager -> Map<Phase, PhaseEventBus>

     */


    @Override
    public <T extends Event> void publish(Class<T> eventClass, Consumer<T> consumer) {

    }

    @Override
    public <T extends Event> void publish(Class<T> eventClass, EventSettings settings, Consumer<T> consumer) {

    }

    @Override
    public void unregisterAll() {

    }

}