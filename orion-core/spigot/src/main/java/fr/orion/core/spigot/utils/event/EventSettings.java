package fr.orion.core.spigot.utils.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.event.EventPriority;

@Getter
@AllArgsConstructor
public class EventSettings {

    private final EventPriority priority;
    private final boolean ignoreCancelled;

}