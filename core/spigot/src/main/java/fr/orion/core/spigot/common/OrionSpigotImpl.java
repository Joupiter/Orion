package fr.orion.core.spigot.common;

import fr.orion.api.utils.threading.MultiThreading;
import fr.orion.core.spigot.common.api.OrionSpigotApi;
import fr.orion.core.spigot.common.benchmark.MongoBench;
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
        // TODO: getBenchHandler().addCategories(String package);
        getBenchHandler().addCategories(new RedisBench(), new ReactorBench(), new MongoBench());
    }

    @Override
    public void unload() {
        getEventBus().unregisterAll();
        getDatabaseLoader().disconnect();
        MultiThreading.shutdown();
    }

}
