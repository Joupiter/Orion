package fr.orion.core.spigot.common.api;

import fr.orion.api.OrionApi;

public abstract class OrionSpigotApi implements OrionApi {

    public abstract void load();

    public abstract void unload();

}
