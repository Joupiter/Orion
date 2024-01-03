package fr.orion.core.spigot.api;

import fr.orion.api.OrionApi;
import org.bukkit.command.CommandSender;

public abstract class OrionSpigotApi implements OrionApi {

    public void s(CommandSender player) {
        player.sendMessage("cc");
    }

}
