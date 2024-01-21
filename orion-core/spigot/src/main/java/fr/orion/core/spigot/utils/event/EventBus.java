package fr.orion.core.spigot.utils.event;

import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;

import java.util.function.Consumer;

public interface EventBus {

    <T extends Event> void publish(Class<T> eventClass, Consumer<T> consumer);

    <T extends Event> void publish(Class<T> eventClass, EventSettings settings, Consumer<T> consumer);

    default EventSettings getDefaultSettings() {
        return new EventSettings(EventPriority.NORMAL, false);
    }

    void unregisterAll();

}