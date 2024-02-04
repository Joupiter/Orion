Need to make an abstract class like [Game](orion-games/engine/src/main/java/fr/orion/game/engine/Game.java) for ffa game

Without team and phase system

```java
public abstract class FFAGame<G extends GamePlayer, S extends GameSettings> {

    private final String name, id;
    private final S settings;
    
    private final ConcurrentMap<UUID, G> players;
    
    public FFAGame(String name, S settings) {
        
    }
    
}
```
