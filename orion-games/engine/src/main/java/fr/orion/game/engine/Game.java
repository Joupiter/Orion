package fr.orion.game.engine;

import fr.orion.api.utils.Utils;
import fr.orion.game.engine.event.GamePlayerJoinEvent;
import fr.orion.game.engine.event.GamePlayerLeaveEvent;
import fr.orion.game.engine.phase.GamePhaseManager;
import fr.orion.game.engine.team.GameTeam;
import fr.orion.game.engine.team.GameTeamColor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Getter
@Setter
public abstract class Game<G extends GamePlayer, T extends GameTeam, S extends GameSettings> extends SimpleGame<G, S> {

    private final GamePhaseManager<?> phaseManager;
    private final List<T> teams;

    private GameState state;

    public Game(String name, S settings) {
        super(name, settings);
        this.phaseManager = new GamePhaseManager<>(this);
        this.teams = new ArrayList<>();
        this.load();
    }

    public abstract T defaultGameTeam(GameTeamColor teamColor);

    private void load() {
        getTeams().addAll(Arrays.stream(GameTeamColor.values()).limit(getSettings().getGameSize().getTeamNeeded()).map(this::defaultGameTeam).toList());
    }

    @Override
    public void unload() {
        super.unload();
        getPhaseManager().unregisterPhases();
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

    private Predicate<GamePlayer> haveTeamPredicate() {
        return gamePlayer -> getTeam(gamePlayer).hasElement().blockOptional().orElse(false);
    }

    public boolean haveTeam(G gamePlayer) {
        return getTeam(gamePlayer).hasElement().blockOptional().orElse(false);
    }

    public boolean oneTeamAlive() {
        return getAliveTeamsCount() == 1;
    }

    public int getAliveTeamsCount() {
        return getAliveTeams().size();
    }

    public int getTeamsCount() {
        return getTeams().size();
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