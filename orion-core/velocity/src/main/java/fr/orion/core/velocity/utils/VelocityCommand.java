package fr.orion.core.velocity.utils;

import com.velocitypowered.api.command.BrigadierCommand;
import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.proxy.ProxyServer;

public interface VelocityCommand<A extends VelocityAddon> {

    A getPlugin();

    default ProxyServer getServer() {
        return getPlugin().getServer();
    }

    CommandMeta getCommandMeta();

    BrigadierCommand getCommand();

    default void registerCommand() {
        System.out.println("Register: " + getCommand().getNode().getName());
        getServer().getCommandManager().register(getCommandMeta(), getCommand());
    }

}