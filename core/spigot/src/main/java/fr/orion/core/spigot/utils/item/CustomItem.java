package fr.orion.core.spigot.utils.item;

import fr.orion.api.utils.Utils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;
import java.util.function.Predicate;

@Getter
@Setter
@RequiredArgsConstructor
public class CustomItem {

    private final String name;
    private final ItemStack item;

    private boolean commonClick;
    private boolean droppable;

    private Consumer<PlayerInteractEvent> commonClickConsumer, leftClickConsumer, rightClickConsumer;
    private Consumer<PlayerDropItemEvent> dropConsumer;
    private Consumer<PlayerPickupItemEvent> pickupConsumer;

    public CustomItem(String name, ItemStack item, boolean droppable, boolean commonClick) {
        this.name = name;
        this.item = item;
        this.droppable = droppable;
        this.commonClick = commonClick;
        this.commonClickConsumer = event -> {};
        this.leftClickConsumer = event -> {};
        this.rightClickConsumer = event -> {};
        this.dropConsumer = event -> {};
        this.pickupConsumer = event -> {};
    }

    public void onClick(PlayerInteractEvent event) {
        Utils.ifTrue(event.getAction(),
                isCommonClick(), () -> getCommonClickConsumer().accept(event),
                leftClickPredicate(), () -> getLeftClickConsumer().accept(event),
                rightClickPredicate(), () -> getRightClickConsumer().accept(event));
    }

    public void giveItem(Player player, int slot) {
        player.getInventory().setItem(slot, getItem());
    }

    public void giveItem(Player player) {
        giveItem(player, player.getInventory().firstEmpty());
    }

    private Predicate<Action> leftClickPredicate() {
        return action -> action.equals(Action.LEFT_CLICK_AIR) || action.equals(Action.LEFT_CLICK_BLOCK);
    }

    private Predicate<Action> rightClickPredicate() {
        return action -> action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK);
    }

}