package fr.orion.game.engine.utils;

import fr.orion.game.engine.GamePlayer;
import fr.orion.game.engine.GameSettings;
import fr.orion.game.engine.GameSize;
import fr.orion.game.engine.team.GameTeam;
import fr.orion.game.engine.team.GameTeamColor;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.util.UUID;

@UtilityClass
public class GameFactory {

    public GamePlayer newDefaultGamePlayer(UUID uuid, boolean spectator) {
        return new GamePlayer(uuid, spectator) {};
    }

    public GameTeam newDefaultGameTeam(GameTeamColor teamColor) {
        return new GameTeam(teamColor) {};
    }

    public GameSettings newDefaultGameSettings(GameSize gameSize, World world) {
        return new GameSettings(gameSize, world) {};
    }

    public GameSettings newDefaultGameSettings(GameSizeTemplate template, World world) {
        return new GameSettings(template, world) {};
    }

    public GameSettings newDefaultGameSettings(GameSizeTemplate template, String worldName) {
        return new GameSettings(template, Bukkit.getWorld(worldName)) {};
    }

}