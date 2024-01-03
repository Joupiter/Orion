TODO: Velocity Api and Spigot Api

Plugin:
```java
public class Example extends JavaPlugin {

    private OrionSpigotApi api;

    @Override
    public void onEnable() {
        Document document = new Document("key", "value");
        Mono.from(getApi().getDatabaseLoader().getMongoDatabase().getDatabase().getCollection("example").insertOne(document)).then();
    }

    public OrionSpigotApi getApi() {
        return (OrionSpigotApi) OrionApi.getProvider();
    }
    
}
```