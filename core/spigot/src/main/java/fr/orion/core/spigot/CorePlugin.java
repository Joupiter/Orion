package fr.orion.core.spigot;

import fr.orion.api.OrionApi;
import fr.orion.core.spigot.common.api.OrionSpigotApi;
import fr.orion.core.spigot.command.BenchmarkingCommand;
import fr.orion.core.spigot.common.OrionSpigotImpl;
import org.bukkit.plugin.java.JavaPlugin;

public class CorePlugin extends JavaPlugin {

    @Override
    public void onLoad() {
        OrionApi.setProvider(new OrionSpigotImpl());
    }

    @Override
    public void onEnable() {
        getApi().load();
        getCommand("bench").setExecutor(new BenchmarkingCommand(this, getApi().getBenchHandler()));
    }

    public OrionSpigotApi getApi() {
        return (OrionSpigotApi) OrionApi.getProvider();
    }

    @Override
    public void onDisable() {
        getApi().unload();
    }

}
