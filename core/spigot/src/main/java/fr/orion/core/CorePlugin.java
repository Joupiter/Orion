package fr.orion.core;

import fr.orion.api.OrionApi;
import fr.orion.core.command.BenchmarkingCommand;
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
