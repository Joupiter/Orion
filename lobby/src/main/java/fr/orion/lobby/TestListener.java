package fr.orion.lobby;

import fr.orion.api.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@Getter
@AllArgsConstructor
public class TestListener implements Listener {

    private final LobbyPlugin plugin;

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        getPlugin().getApi().getUserRepository().getUser(player.getUniqueId()).subscribe(user -> {
            addCoins(user);
            player.sendMessage("§aBonjour ! Vous avez §b" + user.getCoins() + " §acoins !");
        });
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        if (event.getMessage().equalsIgnoreCase("!addcoins")) {
            getPlugin().getApi().getUserRepository().getUser(player.getUniqueId()).subscribe(this::agfgdgdfgf);
            event.setCancelled(true);
        }

    }

    private void addCoins(User user) {
        user.setCoins(user.getCoins() + 1);
    }

    private void agfgdgdfgf(User user) {
        addCoins(user);
        Bukkit.getPlayer(user.getUuid()).sendMessage("§7[§aOrion§7] §eNouveau solde §b" + user.getCoins());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

    }

}
