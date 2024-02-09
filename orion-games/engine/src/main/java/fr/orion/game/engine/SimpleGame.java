package fr.orion.game.engine;

import fr.orion.api.utils.Utils;
import fr.orion.core.spigot.utils.SpigotUtils;
import fr.orion.game.engine.event.GamePlayerJoinEvent;
import fr.orion.game.engine.event.GamePlayerLeaveEvent;
import fr.orion.game.engine.team.GameTeam;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Getter
@Setter
public abstract class SimpleGame<G extends GamePlayer, S extends GameSettings> implements MiniGame {

    private final String name, id;
    private final S settings;

    private final ConcurrentMap<UUID, G> players;

    private GameState state;

    public SimpleGame(String name, S settings) {
        this.name = name;
        this.id = Utils.randomString(10);
        this.settings = settings;
        this.players = new ConcurrentHashMap<>();
        this.state = GameState.WAIT;
        this.load();
    }

    public abstract G defaultGamePlayer(UUID uuid, boolean spectator);

    @Override
    public void load() {
        debug("{} loaded", getFullName());
    }

    @Override
    public void unload() {
        debug("{} unloaded", getFullName());
    }

    public List<G> getAlivePlayers() {
        return getPlayers().values().stream().filter(isSpectatorPredicate().negate()).collect(Collectors.toList());
    }

    public List<G> getSpectators() {
        return getPlayers().values().stream().filter(GamePlayer::isSpectator).collect(Collectors.toList());
    }

    public Mono<G> getPlayer(UUID uuid) {
        return Mono.justOrEmpty(getPlayers().get(uuid));
    }

    public void checkSetting(boolean setting, Runnable runnable) {
        checkSetting(setting, runnable, () -> {});
    }

    public void checkSetting(boolean setting, Runnable trueRunnable, Runnable falseRunnable) {
        Utils.BooleanWrapper.of(setting)
                .ifTrue(trueRunnable)
                .ifFalse(falseRunnable);
    }

    public void checkGameState(GameState gameState, Runnable runnable) {
        Utils.ifTrue(getState().equals(gameState), runnable);
    }

    public void ifContainsPlayer(UUID uuid, Consumer<Player> consumer) {
        ifContainsPlayer(Bukkit.getPlayer(uuid), consumer);
    }

    public void ifContainsPlayer(Player player, Consumer<Player> consumer) {
        Mono.just(player).filter(this::containsPlayer).subscribe(consumer);
    }

    public void ifContainsPlayer(Player player, Runnable runnable) {
        Utils.ifTrue(containsPlayer(player), runnable);
    }

    public void joinGame(Player player) {
        joinGame(player, false);
    }

    @Override
    public void joinGame(Player player, boolean spectator) {
        Utils.ifFalse(getPlayers().containsKey(player.getUniqueId()), () -> {
            G gamePlayer = defaultGamePlayer(player.getUniqueId(), spectator);
            getPlayers().put(player.getUniqueId(), gamePlayer);
            Bukkit.getServer().getPluginManager().callEvent(new GamePlayerJoinEvent<>(this, gamePlayer));
            debug("{} {} {} game", player.getName(), (gamePlayer.isSpectator() ? "spectate" : "join"), getFullName());
        });
    }

    @Override
    public void leaveGame(UUID uuid) {
        getPlayer(uuid).subscribe(gamePlayer -> {
            Bukkit.getServer().getPluginManager().callEvent(new GamePlayerLeaveEvent<>(this, gamePlayer));
            getPlayers().remove(uuid);
            debug("{} leave {}", gamePlayer.getPlayer().getName(), getFullName());
        });
    }

    @Override
    public void endGame() {
        getPlayers().values().stream().map(GamePlayer::getUuid).forEach(this::leaveGame);
        unload();
        //gameManager.removeGame(this);
        debug("END OF GAME : {}", getFullName());
    }

    public void broadcast(String message) {
        getPlayers().values().forEach(gamePlayer -> gamePlayer.sendMessage(message));
    }

    public void broadcast(String message, Object... arguments) {
        broadcast(String.format(message, arguments));
    }

    public void broadcast(String... messages) {
        Arrays.asList(messages)
                .forEach(this::broadcast);
    }

    public void broadcast(Predicate<G> filter, String... messages) {
        getPlayers().values().stream()
                .filter(filter)
                .forEach(gamePlayer -> gamePlayer.sendMessage(messages));
    }

    public void broadcast(Predicate<G> filter, String message, Object... arguments) {
        getPlayers().values().stream()
                .filter(filter)
                .forEach(gamePlayer -> gamePlayer.sendMessage(String.format(message, arguments)));
    }

    public String getFullName() {
        return getName() + "-" + getSettings().getGameSize().getName() + "-" + getId();
    }

    public Predicate<GameTeam> isNoPlayersAlivePredicate() {
        return gameTeam -> gameTeam.getAlivePlayers().isEmpty();
    }

    public Predicate<GamePlayer> isSpectatorPredicate() {
        return GamePlayer::isSpectator;
    }

    public boolean containsPlayer(UUID uuid) {
        return getPlayers().containsKey(uuid);
    }

    public boolean containsPlayer(Player player) {
        return getPlayers().containsKey(player.getUniqueId());
    }

    public boolean canStart() {
        return getAlivePlayersCount() >= getSettings().getGameSize().getMinPlayer();
    }

    public boolean isFull() {
        return getAlivePlayersCount() == getSettings().getGameSize().getMaxPlayer();
    }

    public boolean canJoin() {
        return getAlivePlayersCount() < getSettings().getGameSize().getMaxPlayer();
    }

    public int getAlivePlayersCount() {
        return getAlivePlayers().size();
    }

    public int getSpectatorsCount() {
        return getSpectators().size();
    }

    public int getSize() {
        return getPlayers().size();
    }

    public String coloredMessage(String message) {
        return SpigotUtils.colorize(message);
    }

    @Override
    public void sendDebugInfoMessage(Player player) {
        player.sendMessage("-----------------------------");
        player.sendMessage("Game: " + getFullName());
        player.sendMessage("Size: type=" + getSettings().getGameSize().getName() + ", min=" + getSettings().getGameSize().getMinPlayer() + ", max=" + getSettings().getGameSize().getMaxPlayer() + ", tn=" + getSettings().getGameSize().getTeamNeeded() + ", tm=" + getSettings().getGameSize().getTeamMaxPlayer());
        player.sendMessage("State: " + getState());

        player.sendMessage("Locations: ");
        getSettings().getLocations().forEach((s, locations) -> player.sendMessage(s + ": " + locations.stream().map(Location::toString).collect(Collectors.joining(", "))));

        player.sendMessage("Players: " + getSize() + " (" + getAlivePlayersCount() + "|" + getSpectatorsCount() + ")");
        player.sendMessage("Alive players: " + getAlivePlayers().stream().map(GamePlayer::getPlayer).map(Player::getName).collect(Collectors.joining(", ")));
        player.sendMessage("Spectator players: " + getSpectators().stream().map(GamePlayer::getPlayer).map(Player::getName).collect(Collectors.joining(", ")));
        player.sendMessage("-----------------------------");
    }

}