package fr.orion.core.spigot.common.gui;

import fr.orion.api.utils.threading.MultiThreading;
import fr.orion.core.spigot.utils.gui.GuiButton;
import fr.orion.core.spigot.utils.gui.PageableGui;
import fr.orion.core.spigot.utils.item.ItemBuilder;
import lombok.Getter;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.management.ManagementFactory;

@Getter
public class ThreadGui extends PageableGui<JavaPlugin, GuiButton> {

    private final Player player;

    public ThreadGui(JavaPlugin plugin, Player player) {
        super(plugin, "&6Threads", true, 5, 36);
        this.player = player;
        Thread.getAllStackTraces().keySet().forEach(this::addThreadButton);
    }

    @Override
    public void setup() {
        setInventoryName(getPlayer(), "&6Threads &7(" + getPage().getNumber() + "&7/" + getPagination().getPages().size() + "&7)");
        setHorizontalLine(36, 44, new ItemBuilder(Material.STAINED_GLASS_PANE).setDyeColor(DyeColor.CYAN).build());

        getPage().getElements().forEach(this::addItem);

        setItem(39, previousPageButton());
        setItem(40, getInformationsButton());
        setItem(41, nextPageButton());
    }

    @Override
    public void onUpdate() {
        setItem(40, getInformationsButton());
    }

    private void addThreadButton(Thread thread) {
        getPagination().addElement(new GuiButton(getThreadItem(thread)));
    }

    private ItemStack getThreadItem(Thread thread) {
        return new ItemBuilder(Material.PAPER)
                .setName("&7» &a" + thread.getName())
                .addLore("&7▏ &eState&7: &b" + thread.getState(),
                        "&7▏ &ePriority&7: &c" + thread.getPriority(),
                        "&7▏ &eDeamon&7: &9" + thread.isDaemon(),
                        "&7▏ &eCPU Time&7: &6" + (ManagementFactory.getThreadMXBean().getThreadCpuTime(thread.getId()) / 1000000) + " ms").build();
    }

    private GuiButton getInformationsButton() {
        return new GuiButton(new ItemBuilder(Material.BOOK)
                .setName("&7» &aInformations")
                .addLore("&7▏ &ePool Size&7: &a" + Thread.activeCount(),
                        "&7▏ &eOrion Pool Size&7: &a" + MultiThreading.getTotal(),
                        "&7▏ &eHeapMemory Usage&7: &2" + (ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getUsed() / 1024) / 1024 + " MB",
                        "&7▏ &eNonHeapMemory Usage&7: &2" + (ManagementFactory.getMemoryMXBean().getNonHeapMemoryUsage().getUsed() / 1024) / 1024 + " MB",
                        "&7▏ &eThread Peak&7: &9" + ManagementFactory.getThreadMXBean().getPeakThreadCount()).build());
    }

    @Override
    public GuiButton nextPageButton() {
        return new GuiButton(new ItemBuilder(Material.ARROW).setName("&aSuivant").build(), event -> {
            if (getPagination().hasNext(getPage()))
                updatePage(getPagination().getNext(getPage()));
        });
    }

    @Override
    public GuiButton previousPageButton() {
        return new GuiButton(new ItemBuilder(Material.ARROW).setName("&cRetour").build(), event -> {
            if (getPagination().hasPrevious(getPage()))
                updatePage(getPagination().getPrevious(getPage()));
        });
    }

}