package fr.orion.game.engine;

import fr.orion.game.engine.utils.GameSizeTemplate;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.World;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;

@Getter
@Setter
public abstract class GameSettings {

    private final GameSize gameSize;
    private World world;

    private final ConcurrentMap<String, List<Location>> locations;

    public GameSettings(GameSize gameSize, World world) {
        this.gameSize = gameSize;
        this.world = world;
        this.locations = new ConcurrentHashMap<>();
    }

    public GameSettings(GameSizeTemplate template, World world) {
        this(new GameSize(template), world);
    }

    public void addLocation(String name, Location location) {
        getLocations().computeIfAbsent(name, k -> new ArrayList<>()).add(location);
    }

    public void addLocations(String name, Location... locations) {
        Flux.just(locations).subscribe(location -> addLocation(name, location));
    }

    public Mono<Location> getLocation(String name) {
        return Mono.justOrEmpty(getLocations(name).get(0));
    }

    public void getLocation(String name, Consumer<Location> consumer) {
        getLocation(name).subscribe(consumer);
    }

    public Mono<Location> getRandomLocation(String name) {
        return Mono.justOrEmpty(getLocations(name).stream().skip(getLocations(name).isEmpty() ? 0 : ThreadLocalRandom.current().nextInt(getLocations(name).size())).findFirst());
    }

    public List<Location> getLocations(String name) {
        return getLocations().get(name);
    }

}