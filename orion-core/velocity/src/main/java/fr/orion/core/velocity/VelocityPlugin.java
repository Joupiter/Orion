package fr.orion.core.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import fr.orion.api.OrionApi;
import fr.orion.core.velocity.api.OrionVelocityApi;
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
public class VelocityPlugin {

    private final ProxyServer server;
    private final Logger logger;

    @Inject
    public VelocityPlugin(ProxyServer server, Logger logger) {
        this.server = server;
        this.logger = logger;
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        OrionApi.setProvider(new OrionVelocityImpl());
        getApi().load();
    }

    @Subscribe
    public void onProxyInitialization(ProxyShutdownEvent event) {
        getApi().unload();
    }

    public OrionVelocityApi getApi() {
        return (OrionVelocityApi) OrionApi.getProvider();
    }

}