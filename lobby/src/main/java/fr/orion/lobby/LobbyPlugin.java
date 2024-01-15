package fr.orion.lobby;

import fr.orion.api.OrionApi;
import fr.orion.core.spigot.common.api.OrionSpigotApi;
import fr.orion.lobby.common.LobbyManager;
import fr.orion.lobby.listener.TestListener;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class LobbyPlugin extends JavaPlugin {

    private LobbyManager lobbyManager;

    @Override
    public void onEnable() {
        this.lobbyManager = new LobbyManager(this);
        getServer().getPluginManager().registerEvents(new TestListener(this), this);
    }

    public OrionSpigotApi getApi() {
        return (OrionSpigotApi) OrionApi.getProvider();
    }

    @Override
    public void onDisable() { }

}