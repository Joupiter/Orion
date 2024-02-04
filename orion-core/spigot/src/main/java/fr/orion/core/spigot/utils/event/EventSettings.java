package fr.orion.core.spigot.utils.event;

import org.bukkit.event.EventPriority;

public record EventSettings(EventPriority priority, boolean ignoreCancelled) {}