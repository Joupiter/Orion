package fr.orion.core.utils.gui;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Getter
public class GuiManager implements Listener {

    private final ConcurrentMap<UUID, Gui<?>> guis;

    public GuiManager(JavaPlugin plugin) {
        this.guis = new ConcurrentHashMap<>();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        //MultiThreading.runnablePool.scheduleAtFixedRate(this::updateButtons, 1, 1, TimeUnit.SECONDS);
    }

    private void updateButtons() {
        getGuis().values().forEach(Gui::onUpdate);
    }

    public void open(Player player, Gui<?> gui) {
        getGuis().put(player.getUniqueId(), gui);
        Optional.ofNullable(getGuis().get(player.getUniqueId())).ifPresent(menu -> menu.onOpen(player));
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        ItemStack itemStack = event.getCurrentItem();

        if (itemStack == null) return;

        Optional.ofNullable(getGuis().get(event.getWhoClicked().getUniqueId()))
                .filter(gui -> event.getInventory().equals(gui.getInventory()))
                .ifPresent(gui -> {
                    if (itemStack.getType() == Material.SKULL_ITEM)
                        gui.getButtons().entrySet().stream()
                                .filter(entry -> entry.getValue().getItemStack().hasItemMeta() && entry.getValue().getItemStack().getItemMeta().getDisplayName().equals(itemStack.getItemMeta().getDisplayName()))
                                .findFirst().ifPresent(entry -> entry.getValue().getClickEvent().accept(event));
                    else
                        gui.getButtons().entrySet().stream()
                                .filter(entry -> entry.getValue().getItemStack().equals(itemStack))
                                .findFirst().ifPresent(entry -> entry.getValue().getClickEvent().accept(event));

                    event.setCancelled(true);
                });
    }

    @EventHandler
    public void onDrag(InventoryDragEvent event) {
        Optional.ofNullable(getGuis().get(event.getWhoClicked().getUniqueId()))
                .filter(gui -> event.getInventory().equals(gui.getInventory()))
                .ifPresent(gui -> event.setCancelled(true));
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        Optional.ofNullable(getGuis().get(event.getPlayer().getUniqueId()))
                .filter(gui -> event.getInventory().equals(gui.getInventory()))
                .filter(gui -> gui.getCloseConsumer() != null)
                .ifPresent(gui -> gui.getCloseConsumer().accept(event));
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        getGuis().remove(event.getPlayer().getUniqueId());
    }

}