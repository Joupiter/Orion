package fr.orion.core;

import fr.orion.api.OrionApi;
import org.bukkit.plugin.java.JavaPlugin;

public class CorePlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        OrionApi.setProvider(new OrionImpl());
    }

    public OrionApi getApi() {
        return OrionApi.getProvider();
    }

    @Override
    public void onDisable() {

    }

}
