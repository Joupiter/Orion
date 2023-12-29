package fr.orion.core;

import fr.orion.api.OrionApi;
import org.bukkit.plugin.java.JavaPlugin;

public class CorePlugin extends JavaPlugin {

    @Override
    public void onLoad() {
        OrionApi.setProvider(new OrionImpl());
    }

    @Override
    public void onEnable() {
        getApi().getDatabaseLoader().connect();
    }

    public OrionApi getApi() {
        return OrionApi.getProvider();
    }

    @Override
    public void onDisable() {
        getApi().getDatabaseLoader().disconnect();
    }

}
