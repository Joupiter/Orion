package fr.orion.lobby;

import fr.orion.api.user.User;
import fr.orion.core.common.database.redis.channel.ExampleChannel;
import fr.orion.core.common.database.redis.channel.FineChannel;
import fr.orion.core.common.database.redis.packet.ExamplePacket;
import fr.orion.core.common.database.redis.packet.FinePacket;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import reactor.core.publisher.Mono;

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

        if (event.getMessage().equalsIgnoreCase("!mongo")) {
            Mono.from(getPlugin().getApi().getDatabaseLoader().getMongoDatabase().getDatabase().getCollection("example").insertOne(new Document("k", "v"))).subscribe();
            event.setCancelled(true);
        }

        if (event.getMessage().equalsIgnoreCase("!sub")) {
            getPlugin().getApi().getDatabaseLoader().getRedisMessenger().addChannel(new ExampleChannel());
            event.setCancelled(true);
        }

        if (event.getMessage().equalsIgnoreCase("!sub2")) {
            getPlugin().getApi().getDatabaseLoader().getRedisMessenger().addChannel(new FineChannel());
            event.setCancelled(true);
        }

        if (event.getMessage().equalsIgnoreCase("!pub")) {
            getPlugin().getApi().getDatabaseLoader().getRedisMessenger().publish("example", new ExamplePacket("Fine"));
            event.setCancelled(true);
        }

        if (event.getMessage().equalsIgnoreCase("!pub2")) {
            getPlugin().getApi().getUserRepository().getUser(player.getUniqueId()).subscribe(this::sendFinePacket);
            event.setCancelled(true);
        }

        if (event.getMessage().equalsIgnoreCase("!redistest")) {
            getPlugin().getApi().getDatabaseLoader().getRedisDatabase().getReactiveCommands().set("test", "work").subscribe();
            event.setCancelled(true);
        }

    }

    private void addCoins(User user) {
        user.setCoins(user.getCoins() + 1);
    }

    private void sendFinePacket(User user) {
        getPlugin().getApi().getDatabaseLoader().getRedisMessenger().publish("fine", new FinePacket(user));
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
