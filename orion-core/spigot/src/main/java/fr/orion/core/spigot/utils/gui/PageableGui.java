package fr.orion.core.spigot.utils.gui;

import fr.orion.api.utils.Pagination;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public abstract class PageableGui<P extends JavaPlugin, E> extends Gui<P> {

    private final int maxItems;

    private final Pagination<E> pagination;
    @Setter private Pagination<E>.Page page;

    public PageableGui(P plugin, String inventoryName, boolean updatable, int rows, int maxItems) {
        super(plugin, inventoryName, rows, updatable);
        this.maxItems = maxItems;
        this.pagination = new Pagination<>(getMaxItems());
        this.page = pagination.getPage(1);
    }

    public PageableGui(P plugin, String inventoryName, int rows, int maxItems) {
        this(plugin, inventoryName, false, rows, maxItems);
    }

    public abstract GuiButton nextPageButton();

    public abstract GuiButton previousPageButton();

    public void updatePage(Pagination<E>.Page page) {
        setPage(page);
        refresh();
    }

}