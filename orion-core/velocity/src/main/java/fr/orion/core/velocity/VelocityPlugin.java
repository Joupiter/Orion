package fr.orion.core.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import fr.orion.api.OrionApi;
import fr.orion.core.velocity.utils.VelocityAddon;
import fr.orion.core.velocity.common.OrionVelocityImpl;
import lombok.Getter;
import org.slf4j.Logger;

@Getter
@Plugin(
        id = "orioncore",
        name = "OrionCore",
        version = "1.0.0-SNAPSHOT",
        authors = {"Joupi"}
)
public class VelocityPlugin implements VelocityAddon {

    private final ProxyServer server;
    private final Logger logger;

    @Inject
    public VelocityPlugin(ProxyServer server, Logger logger) {
        this.server = server;
        this.logger = logger;
    }

    @Override
    @Subscribe
    public void onEnable(ProxyInitializeEvent event) {
        OrionApi.setProvider(new OrionVelocityImpl());
        getApi().load();
        registerCommands(this, "fr.orion.core.velocity.command");
        getLogger().info("Hello world!");
    }

    @Override
    @Subscribe
    public void onDisable(ProxyShutdownEvent event) {
        getApi().unload();
        getLogger().info("goodbye!");
    }

}