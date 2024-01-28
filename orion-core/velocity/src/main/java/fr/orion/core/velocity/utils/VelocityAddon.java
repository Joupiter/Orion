package fr.orion.core.velocity.utils;

import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.proxy.ProxyServer;
import fr.orion.api.OrionApi;
import fr.orion.api.utils.Utils;
import fr.orion.core.velocity.api.OrionVelocityApi;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;
import org.slf4j.Logger;

public interface VelocityAddon {

    void onEnable(ProxyInitializeEvent event);

    void onDisable(ProxyShutdownEvent event);

    ProxyServer getServer();

    Logger getLogger();

    default <A extends VelocityAddon> void registerCommands(A plugin, String... packages) {
        try (ScanResult scanResult = new ClassGraph().enableAllInfo().acceptPackages(packages).scan()) {
            scanResult.getClassesImplementing(VelocityCommand.class).stream()
                    .map(Utils::getClassFromClassInfo)
                    .forEach(clazz -> registerCommand(plugin, clazz));
        }
    }

    private <A extends VelocityAddon, T> void registerCommand(A plugin, Class<T> clazz) {
        try {
            ((VelocityCommand<?>) clazz.getDeclaredConstructor(plugin.getClass()).newInstance(plugin)).registerCommand();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    default OrionVelocityApi getApi() {
        return (OrionVelocityApi) OrionApi.getProvider();
    }

}