package fr.orion.lobby;

import fr.orion.api.OrionApi;
import fr.orion.api.database.redis.pubsub.RedisChannel;
import fr.orion.core.common.database.redis.packet.FinePacket;
import fr.orion.core.spigot.api.OrionSpigotApi;
import fr.orion.lobby.common.LobbyManager;
import fr.orion.lobby.listener.TestListener;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class LobbyPlugin extends JavaPlugin {

    private LobbyManager lobbyManager;

    @Override
    public void onEnable() {
        this.lobbyManager = new LobbyManager(this);
        getServer().getPluginManager().registerEvents(new TestListener(this), this);
        getApi().getDatabaseLoader().getRedisMessenger().addChannel(getFineChannel());
    }

    private RedisChannel<FinePacket> getFineChannel() {
        return RedisChannel.newChannel("fine", FinePacket.class, packet -> System.out.println(packet.getUser().getUuid().toString() + ": " + packet.getUser().getCoins()));
    }

    public OrionSpigotApi getApi() {
        return (OrionSpigotApi) OrionApi.getProvider();
    }

    @Override
    public void onDisable() { }

}