package fr.orion.core.spigot.utils.item;

import fr.orion.api.utils.Utils;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Getter
public class CustomItemManager implements Listener {

    private final JavaPlugin plugin;
    private final ConcurrentMap<String, CustomItem> items;

    public CustomItemManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.items = new ConcurrentHashMap<>();
        getPlugin().getServer().getPluginManager().registerEvents(this, getPlugin());
    }

    public void addItem(CustomItem customItem) {
        getItems().putIfAbsent(customItem.getName(), customItem);
    }

    public Optional<CustomItem> getItem(String name) {
        return Optional.ofNullable(getItems().get(name));
    }

    public Optional<CustomItem> getItem(ItemStack itemStack) {
        return getItems().values().stream().filter(customItem -> customItem.getItem().equals(itemStack)).findFirst();
    }

    public void giveItem(String name, Player player, int slot) {
        getItem(name).ifPresent(customItem -> customItem.giveItem(player, slot));
    }

    public void giveItem(String name, Player player) {
        giveItem(name, player, player.getInventory().firstEmpty());
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        getItem(event.getItem())
                .filter(customItem -> event.getItem() != null)
                .ifPresent(customItem -> customItem.onClick(event));
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        getItem(event.getItemDrop().getItemStack()).ifPresent(customItem ->
                Utils.BooleanWrapper.of(customItem.isDroppable())
                        .ifTrue(() -> customItem.getDropConsumer().accept(event))
                        .ifFalse(() -> event.setCancelled(true)));
    }

    @EventHandler
    public void onPickup(PlayerPickupItemEvent event) {
        getItem(event.getItem().getItemStack())
                .ifPresent(customItem -> customItem.getPickupConsumer().accept(event));
    }

}
