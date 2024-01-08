package fr.orion.core.spigot.common;

import fr.orion.core.spigot.common.api.OrionSpigotApi;
import fr.orion.core.spigot.common.benchmark.ReactorBench;
import fr.orion.core.spigot.common.benchmark.RedisBench;
import org.bukkit.plugin.java.JavaPlugin;

public class OrionSpigotImpl extends OrionSpigotApi {

    public OrionSpigotImpl(JavaPlugin plugin) {
        super(plugin);
    }

    @Override
    public void load() {
        getDatabaseLoader().connect();
        getBenchHandler().addCategories(new RedisBench(), new ReactorBench());
    }

    @Override
    public void unload() {
        getEventBus().unregisterAll();
        getDatabaseLoader().disconnect();
    }

}
