Plugin:
```java
public class Example extends JavaPlugin {

    private OrionSpigotApi api;

    @Override
    public void onEnable() {
        Document document = new Document("key", "value");

        Mono.from(getApi().getDatabaseLoader()
                        .getMongoDatabase()
                        .getDatabase()
                        .getCollection("example")
                        .insertOne(document))
                .publishOn(Schedulers.boundedElastic()) // Make the work on the scalable thread
                .subscribe();
    }

    public OrionSpigotApi getApi() {
        return (OrionSpigotApi) OrionApi.getProvider();
    }
    
}
```

Velocity:
```java
@Getter
@Plugin(
        id = "example",
        name = "Example",
        version = "1.0.0-SNAPSHOT",
        authors = {"Joupi"}
)
public class VelocityPlugin implements VelocityAddon {

    private final ProxyServer server;
    private final Logger logger;

    @Inject
    public VelocityPlugin(ProxyServer server, Logger logger) {
        this.server = server;
        this.logger = logger;
    }

    @Override
    @Subscribe
    public void onEnable(ProxyInitializeEvent event) {
        getLogger().info("Hello world!");
    }

    @Subscribe
    public void onJoin(LoginEvent event) {
        Player player = event.getPlayer();

        getApi().getDatabaseLoader()
                .getRedisDatabase()
                .set(getRedisKey(player), player.getUsername())
                .subscribe();
    }

    private String getRedisKey(Player player) {
        return "user:" + player.getUniqueId().toString();
    }
    
    @Override
    @Subscribe
    public void onDisable(ProxyShutdownEvent event) {
        getLogger().info("goodbye!");
    }

}
```