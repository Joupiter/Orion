package fr.orion.core.spigot.utils.gui;

import fr.orion.api.utils.threading.MultiThreading;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.stream.IntStream;

@Getter
@Setter
public abstract class Gui<P extends JavaPlugin> {

    private final P plugin;

    private final String inventoryName;
    private final int rows;
    private final ConcurrentMap<Integer, GuiButton> buttons;

    private Inventory inventory;
    private Consumer<InventoryCloseEvent> closeConsumer;
    private boolean updatable;

    public Gui(P plugin, String inventoryName, int rows, boolean updatable) {
        this.plugin = plugin;
        this.inventoryName = inventoryName;
        this.rows = rows;
        this.buttons = new ConcurrentHashMap<>();
        this.inventory = Bukkit.createInventory(null, rows * 9, ChatColor.translateAlternateColorCodes('&', inventoryName));
        this.updatable = updatable;
        defaultLoad();
    }

    public Gui(P plugin, String inventoryName, int rows) {
        this(plugin, inventoryName, rows, false);
    }

    public abstract void setup();

    public void onUpdate() {}

    public void onOpen(Player player) {
        setup();
        open(player);
    }

    private void open(Player player) {
        player.openInventory(getInventory());
    }

    public void close(HumanEntity player) {
        player.closeInventory();
    }

    public void setInventoryName(Player player, String name) {
        MultiThreading.schedule(() -> InventoryUpdate.updateInventory(player, ChatColor.translateAlternateColorCodes('&', name)), 20, TimeUnit.MILLISECONDS);
    }

    public void setItem(int slot, GuiButton button) {
        getButtons().put(slot, button);
        update();
    }

    public void setItems(int[] slots, GuiButton button) {
        Arrays.stream(slots).forEach(slot -> setItem(slot, button));
    }

    public void setItems(int[] slots, ItemStack itemStack) {
        setItems(slots, new GuiButton(itemStack));
    }

    public void setItems(List<Integer> slots, GuiButton button) {
        slots.forEach(slot -> setItem(slot, button));
    }

    public void setItems(List<Integer> slots, ItemStack itemStack) {
        setItems(slots, new GuiButton(itemStack));
    }

    public void setHorizontalLine(int from, int to, GuiButton button) {
        IntStream.rangeClosed(from, to)
                .forEach(slot -> setItem(slot, button));
    }

    public void setHorizontalLine(int from, int to, ItemStack item) {
        setHorizontalLine(from, to, new GuiButton(item));
    }

    public void setVerticalLine(int from, int to, GuiButton button) {
        IntStream.iterate(from, slot -> slot + 9)
                .limit((to - from) / 9 + 1)
                .forEach(slot -> setItem(slot, button));
    }

    public void setVerticalLine(int from, int to, ItemStack item) {
        setVerticalLine(from, to, new GuiButton(item));
    }

    public void addItem(GuiButton item) {
        setItem(getInventory().firstEmpty(), item);
        update();
    }

    public void fillAllInventory(GuiButton button) {
        IntStream.range(0, getSize())
                .filter(i -> getItem(i) != null)
                .forEach(i -> setItem(i, button));
    }

    public int[] getBorders() {
        return IntStream.range(0, getSize())
                .filter(i -> getSize() < 27 || i < 9 || i % 9 == 0 || (i - 8) % 9 == 0 || i > getSize() - 9).parallel()
                .toArray();
    }

    public void removeItem(int slot) {
        getButtons().remove(slot);
        getInventory().remove(getInventory().getItem(slot));
    }

    public void clear() {
        getButtons().keySet().forEach(this::removeItem);
        update();
    }

    private void defaultLoad() {
        update();
    }

    private void update() {
        registerItems();
    }

    public void refresh() {
        clear();
        setup();
    }

    private void registerItems() {
        getButtons().forEach((slot, item) -> getInventory().setItem(slot, item.getItemStack()));
    }

    public GuiButton getItem(int slot) {
        return getButtons().get(slot);
    }

    public int getSize() {
        return getRows() * 9;
    }

}