package fr.orion.game.engine.team;

import fr.orion.game.engine.GamePlayer;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

@Getter
@Slf4j
public abstract class GameTeam {

    private final String name;
    private final GameTeamColor color;

    private final List<GamePlayer> members;

    public GameTeam(GameTeamColor color) {
        this.name = color.getName();
        this.color = color;
        this.members = new ArrayList<>();
    }

    public void addMember(GamePlayer gamePlayer) {
        getMembers().add(gamePlayer);
        log.debug("Team - {} added to {} team", gamePlayer.getPlayer().getName(), getName());
    }

    public void removeMember(GamePlayer gamePlayer) {
        getMembers().removeIf(gamePlayer::equals);
        log.debug("Team - {} removed to {} team", gamePlayer.getPlayer().getName(), getName());
    }

    public boolean isMember(GamePlayer gamePlayer) {
        return getMembers().contains(gamePlayer);
    }

    public boolean isMember(UUID uuid) {
        return getMembers().stream().anyMatch(gamePlayer -> gamePlayer.getUuid().equals(uuid));
    }

    public List<GamePlayer> getAlivePlayers() {
        return getMembers().stream().filter(isSpectatorPredicate().negate()).toList();
    }

    private Predicate<GamePlayer> isSpectatorPredicate() {
        return GamePlayer::isSpectator;
    }

    public boolean isNoPlayersAlive() {
        return getAlivePlayers().isEmpty();
    }

    public int getSize() {
        return getMembers().size();
    }

    public String getColoredName() {
        return getColor().getChatColor() + getName();
    }

}