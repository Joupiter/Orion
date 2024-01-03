package fr.orion.core.spigot;

import fr.orion.api.OrionApi;
import fr.orion.core.spigot.api.OrionSpigotApi;
import fr.orion.core.spigot.command.BenchmarkingCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class CorePlugin extends JavaPlugin {

    @Override
    public void onLoad() {
        OrionApi.setProvider(new OrionSpigotImpl());
    }

    @Override
    public void onEnable() {
        getApi().getDatabaseLoader().connect();
        getCommand("bench").setExecutor(new BenchmarkingCommand(this, getApi().getBenchHandler()));
    }

    public OrionSpigotApi getApi() {
        return (OrionSpigotApi) OrionApi.getProvider();
    }

    @Override
    public void onDisable() {
        getApi().getDatabaseLoader().disconnect();
    }

}
