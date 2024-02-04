package fr.orion.game.engine;

import fr.orion.api.utils.Utils;
import fr.orion.core.spigot.utils.SpigotUtils;
import fr.orion.game.engine.event.GamePlayerJoinEvent;
import fr.orion.game.engine.event.GamePlayerLeaveEvent;
import fr.orion.game.engine.phase.GamePhaseManager;
import fr.orion.game.engine.team.GameTeam;
import fr.orion.game.engine.team.GameTeamColor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Getter
@Setter
@Slf4j
public abstract class Game<G extends GamePlayer, T extends GameTeam, S extends GameSettings> {

    private final String name, id;
    private final S settings;

    private final GamePhaseManager<?> phaseManager;

    private final List<T> teams;
    private final ConcurrentMap<UUID, G> players;

    private GameState state;

    public Game(String name, S settings) {
        this.name = name;
        this.id = Utils.randomString(10);
        this.settings = settings;
        this.phaseManager = new GamePhaseManager<>(this){};
        this.teams = new ArrayList<>();
        this.players = new ConcurrentHashMap<>();
        this.state = GameState.WAIT;
        this.load();
    }

    public abstract G defaultGamePlayer(UUID uuid, boolean spectator);

    public abstract T defaultGameTeam(GameTeamColor teamColor);

    private void load() {
        getTeams().addAll(Arrays.stream(GameTeamColor.values()).limit(getSettings().getGameSize().getTeamNeeded()).map(this::defaultGameTeam).toList());
        //Bukkit.getPluginManager().registerEvents(this, getPlugin());
        debug("{} loaded", getFullName());
    }

    public void unload() {
        getPhaseManager().unregisterPhases();
        //getListeners().forEach(HandlerList::unregisterAll);
        //HandlerList.unregisterAll(this);
        debug("{} unloaded", getFullName());
    }

    public List<G> getAlivePlayers() {
        return getPlayers().values().stream().filter(isSpectatorPredicate().negate()).collect(Collectors.toList());
    }

    public List<G> getSpectators() {
        return getPlayers().values().stream().filter(GamePlayer::isSpectator).collect(Collectors.toList());
    }

    public List<G> getPlayersWithTeam() {
        return getPlayers().values().stream().filter(haveTeamPredicate()).toList();
    }

    public List<G> getPlayersWithoutTeam() {
        return getPlayers().values().stream().filter(haveTeamPredicate().negate()).toList();
    }

    public List<T> getAliveTeams() {
        return getTeams().stream().filter(isNoPlayersAlivePredicate().negate()).toList();
    }

    public List<T> getReachableTeams() {
        return getTeams().stream().filter(gameTeam -> gameTeam.getSize() < getSettings().getGameSize().getTeamMaxPlayer()).toList();
    }

    public Mono<T> getTeam(String teamName) {
        return Mono.justOrEmpty(getTeams().stream().filter(gameTeam -> gameTeam.getName().equals(teamName)).findFirst());
    }

    public Mono<T> getTeam(GamePlayer gamePlayer) {
        return Mono.justOrEmpty(getTeams().stream().filter(gameTeam -> gameTeam.isMember(gamePlayer)).findFirst());
    }

    public Mono<T> getRandomTeam() {
        return Mono.justOrEmpty(getReachableTeams().stream()
                .skip(getReachableTeams().isEmpty() ? 0 : ThreadLocalRandom.current().nextInt(getReachableTeams().size()))
                .findFirst());
    }

    private Mono<T> getTeamWithLeastPlayers() {
        return Mono.justOrEmpty(getTeams().stream()
                .filter(team -> team.getSize() < getSettings().getGameSize().getTeamMaxPlayer())
                .min(Comparator.comparingInt(GameTeam::getSize)));
    }

    public void addPlayerToTeam(GamePlayer gamePlayer, GameTeam gameTeam) {
        removePlayerToTeam(gamePlayer);
        gameTeam.addMember(gamePlayer);
    }

    public void removePlayerToTeam(GamePlayer gamePlayer) {
        getTeam(gamePlayer).subscribe(gameTeam -> gameTeam.removeMember(gamePlayer));
    }

    public void fillTeam() {
        getPlayersWithoutTeam().forEach(gamePlayer -> getTeamWithLeastPlayers().subscribe(gameTeam -> gameTeam.addMember(gamePlayer)));
    }

    public Optional<G> getPlayer(UUID uuid) {
        return Optional.ofNullable(getPlayers().get(uuid));
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
        if (getState().equals(gameState))
            runnable.run();
    }

    public void ifContainsPlayer(UUID uuid, Consumer<Player> consumer) {
        ifContainsPlayer(Bukkit.getPlayer(uuid), consumer);
    }

    public void ifContainsPlayer(Player player, Consumer<Player> consumer) {
        Optional.of(player).filter(this::containsPlayer).ifPresent(consumer);
    }

    public void ifContainsPlayer(Player player, Runnable runnable) {
        Utils.ifTrue(containsPlayer(player), runnable);
    }

    public void joinGame(Player player) {
        joinGame(player, false);
    }

    public void joinGame(Player player, boolean spectator) {
        Utils.ifFalse(getPlayers().containsKey(player.getUniqueId()), () -> {
            G gamePlayer = defaultGamePlayer(player.getUniqueId(), spectator);
            getPlayers().put(player.getUniqueId(), gamePlayer);
            Bukkit.getServer().getPluginManager().callEvent(new GamePlayerJoinEvent<>(this, gamePlayer));
            debug("{} {} {} game", player.getName(), (gamePlayer.isSpectator() ? "spectate" : "join"), getFullName());
        });
    }

    public void leaveGame(UUID uuid) {
        getPlayer(uuid).ifPresent(gamePlayer -> {
            Bukkit.getServer().getPluginManager().callEvent(new GamePlayerLeaveEvent<>(this, gamePlayer));
            getPlayers().remove(uuid);
            removePlayerToTeam(gamePlayer);
            debug("{} leave {}", gamePlayer.getPlayer().getName(), getFullName());
        });
    }

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

    public void debug(String message, Object ... arguments) {
        log.debug(message, arguments);
    }

    public String getFullName() {
        return getName() + "-" + getSettings().getGameSize().getName() + "-" + getId();
    }

    private Predicate<GameTeam> isNoPlayersAlivePredicate() {
        return gameTeam -> gameTeam.getAlivePlayers().isEmpty();
    }

    private Predicate<GamePlayer> isSpectatorPredicate() {
        return GamePlayer::isSpectator;
    }

    private Predicate<GamePlayer> haveTeamPredicate() {
        return gamePlayer -> getTeam(gamePlayer).hasElement().blockOptional().orElse(false);
    }

    public boolean haveTeam(G gamePlayer) {
        return getTeam(gamePlayer).hasElement().blockOptional().orElse(false);
    }

    public boolean containsPlayer(UUID uuid) {
        return getPlayers().containsKey(uuid);
    }

    public boolean containsPlayer(Player player) {
        return getPlayers().containsKey(player.getUniqueId());
    }

    public boolean oneTeamAlive() {
        return getAliveTeamsCount() == 1;
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

    public int getAliveTeamsCount() {
        return getAliveTeams().size();
    }

    public int getAlivePlayersCount() {
        return getAlivePlayers().size();
    }

    public int getSpectatorsCount() {
        return getSpectators().size();
    }

    public int getTeamsCount() {
        return getTeams().size();
    }

    public int getSize() {
        return getPlayers().size();
    }

    public String coloredMessage(String message) {
        return SpigotUtils.colorize(message);
    }

    public void sendDebugInfoMessage(Player player) {
        player.sendMessage("-----------------------------");
        player.sendMessage("Game: " + getFullName());
        player.sendMessage("Size: type=" + getSettings().getGameSize().getName() + ", min=" + getSettings().getGameSize().getMinPlayer() + ", max=" + getSettings().getGameSize().getMaxPlayer() + ", tn=" + getSettings().getGameSize().getTeamNeeded() + ", tm=" + getSettings().getGameSize().getTeamMaxPlayer());
        player.sendMessage("State: " + getState());
        Optional.ofNullable(getPhaseManager().getCurrentPhase()).ifPresent(phase -> player.sendMessage("Phase: " + phase.getClass().getSimpleName()));

        player.sendMessage("Locations: ");
        getSettings().getLocations().forEach((s, locations) -> player.sendMessage(s + ": " + locations.stream().map(Location::toString).collect(Collectors.joining(", "))));

        player.sendMessage("Team Alive: " + getAliveTeamsCount());
        player.sendMessage("Teams: " + getTeamsCount());
        getTeams().forEach(gameTeam -> player.sendMessage(gameTeam.getName() + ": " + gameTeam.getMembers().stream().map(GamePlayer::getPlayer).map(Player::getName).collect(Collectors.joining(", "))));

        player.sendMessage("Players: " + getSize() + " (" + getAlivePlayersCount() + "|" + getSpectatorsCount() + ")");
        player.sendMessage("Alive players: " + getAlivePlayers().stream().map(GamePlayer::getPlayer).map(Player::getName).collect(Collectors.joining(", ")));
        player.sendMessage("Spectator players: " + getSpectators().stream().map(GamePlayer::getPlayer).map(Player::getName).collect(Collectors.joining(", ")));
        player.sendMessage("-----------------------------");
    }

}