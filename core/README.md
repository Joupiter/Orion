TODO: Velocity Api and Spigot Api

```java
public abstract class OrionSpigotApi extends OrionImpl { 
    
}
```

Plugin:
```java
public class CorePlugin extends JavaPlugin {
    
    private OrionSpigotApi api;
    
    @Override
    public void onLoad() {
        OrionApi.setProvider(new OrionSpigotApi());
    }

    @Override
    public void onEnable() {
        getApi().getDatabaseLoader().connect();
    }

    @Override
    public void onDisable() {
        getApi().getDatabaseLoader().disconnect();
    }
    
}
```