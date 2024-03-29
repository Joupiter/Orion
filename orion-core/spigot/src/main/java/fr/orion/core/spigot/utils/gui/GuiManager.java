package fr.orion.core.spigot.utils.gui;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
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
        plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin, this::updateButtons, 20, 20);
    }

    public Optional<Gui<?>> getGui(UUID uuid) {
        return Optional.ofNullable(getGuis().get(uuid));
    }

    public Optional<Gui<?>> getGui(HumanEntity player) {
        return getGui(player.getUniqueId());
    }

    private void updateButtons() {
        getGuis().values().stream().filter(Gui::isUpdatable).forEach(Gui::onUpdate);
    }

    public void open(Player player, Gui<?> gui) {
        getGuis().put(player.getUniqueId(), gui);
        getGui(player).ifPresent(menu -> menu.onOpen(player));
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        ItemStack itemStack = event.getCurrentItem();

        if (itemStack == null) return;

        getGui(event.getWhoClicked())
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
        getGui(event.getWhoClicked())
                .filter(gui -> event.getInventory().equals(gui.getInventory()))
                .ifPresent(gui -> event.setCancelled(true));
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        getGui(event.getPlayer())
                .filter(gui -> event.getInventory().equals(gui.getInventory()))
                .ifPresent(gui -> {
                    Optional.ofNullable(gui.getCloseConsumer()).ifPresent(consumer -> consumer.accept(event));
                    getGuis().remove(event.getPlayer().getUniqueId());
                });
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        getGuis().remove(event.getPlayer().getUniqueId());
    }

}