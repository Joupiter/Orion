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
public class LobbyItems {

    private final LobbyPlugin plugin;

    public LobbyItems(LobbyPlugin plugin) {
        this.plugin = plugin;
        this.loadItems();
    }

    public void loadItems() {
        getPlugin().getApi().getItemManager().addItem(getThreadItem());
    }

    public void setup(Player player) {
        getThreadItem().giveItem(player, 0);
    }

    private CustomItem getThreadItem() {
        return new CustomItemBuilder("lobby-game", new ItemBuilder(Material.BOOK).setName("&6Threads"), false, true)
                .setOnCommonClick(event -> getPlugin().getApi().getGuiManager().open(event.getPlayer(), new ThreadGui(getPlugin(), event.getPlayer())))
                .build();
    }

}
