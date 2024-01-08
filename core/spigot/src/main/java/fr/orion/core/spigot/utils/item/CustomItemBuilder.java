package fr.orion.core.spigot.utils.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

import java.util.function.Consumer;

@Getter
@AllArgsConstructor
public class CustomItemBuilder {

    private final CustomItem customItem;

    public CustomItemBuilder(String name, ItemBuilder itemBuilder) {
        this(new CustomItem(name, itemBuilder.build()));
    }

    public CustomItemBuilder(String name, ItemBuilder itemBuilder, boolean droppable, boolean commonClick) {
        this(new CustomItem(name, itemBuilder.build(), droppable, commonClick));
    }

    public CustomItemBuilder setOnCommonClick(Consumer<PlayerInteractEvent> consumer) {
        getCustomItem().setCommonClickConsumer(consumer);
        return this;
    }

    public CustomItemBuilder setOnLeftClick(Consumer<PlayerInteractEvent> consumer) {
        getCustomItem().setLeftClickConsumer(consumer);
        return this;
    }

    public CustomItemBuilder setOnRightClick(Consumer<PlayerInteractEvent> consumer) {
        getCustomItem().setRightClickConsumer(consumer);
        return this;
    }

    public CustomItemBuilder setOnDrop(Consumer<PlayerDropItemEvent> consumer) {
        getCustomItem().setDropConsumer(consumer);
        return this;
    }

    public CustomItemBuilder setOnPickup(Consumer<PlayerPickupItemEvent> consumer) {
        getCustomItem().setPickupConsumer(consumer);
        return this;
    }

    public CustomItemBuilder setCommonClick(boolean commonClick) {
        getCustomItem().setCommonClick(commonClick);
        return this;
    }

    public CustomItemBuilder setDroppable(boolean droppable) {
        getCustomItem().setDroppable(droppable);
        return this;
    }

    public CustomItem build() {
        return getCustomItem();
    }

}