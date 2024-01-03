package fr.orion.core.spigot.utils.item;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;
import java.util.function.Consumer;

@Getter
@Setter
public class CustomItem {

    private final UUID uuid;
    private final ItemStack item;

    private Consumer<PlayerInteractEvent> leftClickConsumer, rightClickConsumer;
    private Consumer<PlayerDropItemEvent> dropConsumer;
    private Consumer<PlayerPickupItemEvent> pickupConsumer;

    public CustomItem(ItemStack item, Consumer<PlayerInteractEvent> leftClickConsumer, Consumer<PlayerInteractEvent> rightClickConsumer, Consumer<PlayerDropItemEvent> dropConsumer, Consumer<PlayerPickupItemEvent> pickupConsumer) {
        this.uuid = UUID.randomUUID();
        this.item = item;
        this.leftClickConsumer = leftClickConsumer;
        this.rightClickConsumer = rightClickConsumer;
        this.dropConsumer = dropConsumer;
        this.pickupConsumer = pickupConsumer;
    }

}
