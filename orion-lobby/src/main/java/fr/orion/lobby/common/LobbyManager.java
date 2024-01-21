package fr.orion.lobby.common;

import fr.orion.core.spigot.common.gui.ThreadGui;
import fr.orion.core.spigot.utils.item.CustomItem;
import fr.orion.core.spigot.utils.item.CustomItemBuilder;
import fr.orion.core.spigot.utils.item.ItemBuilder;
import fr.orion.lobby.LobbyPlugin;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.entity.Player;

@Getter
public class LobbyManager {

    private final LobbyPlugin plugin;
    private final LobbyBoard board;

    public LobbyManager(LobbyPlugin plugin) {
        this.plugin = plugin;
        this.board = new LobbyBoard(plugin);
        this.loadItems();
    }

    public void loadItems() {
        getPlugin().getApi().getItemManager().addItem(getThreadItem());
    }

    public void setup(Player player) {
        getBoard().addViewer(player);
        getThreadItem().giveItem(player, 0);
    }

    private CustomItem getThreadItem() {
        return new CustomItemBuilder("thread", new ItemBuilder(Material.BOOK).setName("&6Threads"), false, true)
                .setOnClick(event -> getPlugin().getApi().getGuiManager().open(event.getPlayer(), new ThreadGui(getPlugin(), event.getPlayer())))
                .build();
    }

}