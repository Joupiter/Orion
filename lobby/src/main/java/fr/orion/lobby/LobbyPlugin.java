package fr.orion.lobby;

import fr.orion.api.OrionApi;
import fr.orion.core.command.BenchmarkingCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class LobbyPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new TestListener(this), this);
    }

    public OrionApi getApi() {
        return OrionApi.getProvider();
    }

    @Override
    public void onDisable() { }

}