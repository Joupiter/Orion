package fr.orion.core.spigot.api;

import fr.orion.api.OrionApi;
import fr.orion.api.benchmark.BenchHandler;
import fr.orion.api.database.DatabaseLoader;
import fr.orion.api.rank.RankRepository;
import fr.orion.api.user.UserRepository;
import fr.orion.core.common.benchmark.BenchManager;
import fr.orion.core.common.database.DatabaseManager;
import fr.orion.core.common.rank.InMemoryRankManager;
import fr.orion.core.common.user.InMemoryUserManager;
import fr.orion.core.spigot.utils.event.EventBus;
import fr.orion.core.spigot.utils.event.SpigotEventBus;
import fr.orion.core.spigot.utils.gui.GuiManager;
import fr.orion.core.spigot.utils.item.CustomItemManager;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public abstract class OrionSpigotApi implements OrionApi {

    private final DatabaseLoader databaseLoader;

    private final UserRepository userRepository;
    private final RankRepository rankRepository;

    private final BenchHandler benchHandler;
    private final EventBus eventBus;

    private final GuiManager guiManager;
    private final CustomItemManager itemManager;

    public OrionSpigotApi(JavaPlugin plugin) {
        this.databaseLoader = new DatabaseManager();
        this.userRepository = new InMemoryUserManager();
        this.rankRepository = new InMemoryRankManager();
        this.benchHandler = new BenchManager();
        this.eventBus = new SpigotEventBus(plugin);
        this.guiManager = new GuiManager(plugin);
        this.itemManager = new CustomItemManager(plugin);
    }

    public abstract void load();

    public abstract void unload();

    public static OrionSpigotApi getProvider() {
        return (OrionSpigotApi) Provider.getProvider();
    }

}