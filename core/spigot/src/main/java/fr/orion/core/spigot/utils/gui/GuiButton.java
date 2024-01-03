package fr.orion.core.spigot.utils.gui;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

@Getter
@Setter
@AllArgsConstructor
public class GuiButton {

    private final ItemStack itemStack;
    private Consumer<InventoryClickEvent> clickEvent;

    public GuiButton(ItemStack itemStack) {
        this(itemStack, event -> event.setCancelled(true));
    }

}