package fr.orion.lobby.listener;

import fr.orion.api.user.User;
import fr.orion.core.common.database.redis.packet.ExamplePacket;
import fr.orion.core.common.database.redis.packet.FinePacket;
import fr.orion.core.spigot.common.channel.ExampleChannel;
import fr.orion.lobby.LobbyPlugin;
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
import org.bukkit.event.player.PlayerToggleSneakEvent;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Getter
@AllArgsConstructor
public class TestListener implements Listener {

    private final LobbyPlugin plugin;

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        getPlugin().getApi().getUserRepository().getUser(player.getUniqueId()).subscribe(user -> {
            addCoins(user);
            getPlugin().getLobbyManager().setup(player);
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

        if (event.getMessage().equalsIgnoreCase("!eventbus")) {
            getPlugin().getApi().getEventBus().publish(PlayerToggleSneakEvent.class, sneakEvent -> sneakEvent.getPlayer().sendMessage("Work!"));
            event.setCancelled(true);
        }

        if (event.getMessage().equalsIgnoreCase("!eventbus unregister")) {
            getPlugin().getApi().getEventBus().unregisterAll();
            event.setCancelled(true);
        }

        if (event.getMessage().equalsIgnoreCase("!mongo")) {
            Mono.from(getPlugin().getApi().getDatabaseLoader().getMongoDatabase().getDatabase()
                    .getCollection("example")
                    .insertOne(new Document("k", "v")))
                    .subscribeOn(Schedulers.boundedElastic()).subscribe();
            event.setCancelled(true);
        }

        if (event.getMessage().equalsIgnoreCase("!sub")) {
            getPlugin().getApi().getDatabaseLoader().getRedisMessenger().addChannel(new ExampleChannel());
            event.setCancelled(true);
        }

        if (event.getMessage().equalsIgnoreCase("!pub")) {
            getPlugin().getApi().getDatabaseLoader().getRedisMessenger()
                    .publish("example", new ExamplePacket("Fine"));
            event.setCancelled(true);
        }

        if (event.getMessage().equalsIgnoreCase("!pub2")) {
            getPlugin().getApi().getUserRepository().getUser(player.getUniqueId()).subscribe(this::sendFinePacket);
            event.setCancelled(true);
        }

        if (event.getMessage().equalsIgnoreCase("!redistest")) {
            getPlugin().getApi().getDatabaseLoader().getRedisDatabase().getReactiveCommands()
                    .set("test", "work")
                    .doOnNext(s -> System.out.println("[Redis] Operate on " + Thread.currentThread().getName()))
                    .subscribe();
            event.setCancelled(true);
        }

    }

    private void addCoins(User user) {
        user.getCoins().add(1);
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

        getPlugin().getLobbyManager().getBoard().removeViewer(player);
    }

}