package fr.orion.core.spigot.utils.event;

import fr.orion.api.utils.Utils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.function.Consumer;

@Getter
@AllArgsConstructor
public class SpigotEventBus implements EventBus, Listener {

    private final JavaPlugin plugin;

    @Override
    public <T extends Event> void publish(Class<T> eventClass, Consumer<T> consumer) {
        publish(eventClass, getDefaultSettings(), consumer);
    }

    @Override
    public <T extends Event> void publish(Class<T> eventClass, EventSettings settings, Consumer<T> consumer) {
        getPlugin().getServer().getPluginManager().registerEvent(eventClass, this, settings.getPriority(), getEventExecutor(eventClass, consumer), getPlugin(), settings.isIgnoreCancelled());
    }

    @Override
    public void unregisterAll() {
        HandlerList.unregisterAll(this);
    }

    private <T extends Event> EventExecutor getEventExecutor(Class<T> eventClass, Consumer<T> consumer) {
        return (listener, event) -> Utils.ifTrue(eventClass.isInstance(event), () -> consumer.accept(eventClass.cast(event)));
    }

}