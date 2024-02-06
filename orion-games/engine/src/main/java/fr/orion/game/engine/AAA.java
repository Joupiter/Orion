package fr.orion.game.engine;

import fr.orion.game.engine.team.GameTeam;
import fr.orion.game.engine.team.GameTeamColor;
import fr.orion.game.engine.utils.GameFactory;
import fr.orion.game.engine.utils.GameSizeTemplate;

import java.util.UUID;

public class AAA extends Game<GamePlayer, GameTeam, GameSettings> {

    public AAA() {
        super("name", GameFactory.newDefaultGameSettings(GameSizeTemplate.SIZE_1V1, "world"));
    }

    @Override
    public GamePlayer defaultGamePlayer(UUID uuid, boolean spectator) {
        return GameFactory.newDefaultGamePlayer(uuid, spectator);
    }

    @Override
    public GameTeam defaultGameTeam(GameTeamColor teamColor) {
        return GameFactory.newDefaultGameTeam(teamColor);
    }

}