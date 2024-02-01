package fr.orion.core.spigot.utils.event;

import lombok.Getter;
import org.bukkit.event.EventPriority;

@Getter
public record EventSettings(EventPriority priority, boolean ignoreCancelled) {}