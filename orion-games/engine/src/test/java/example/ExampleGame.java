package example;

import fr.orion.game.engine.SimpleGame;
import fr.orion.game.engine.utils.GameSizeTemplate;

import java.util.UUID;

public class ExampleGame extends SimpleGame<ExampleGamePlayer, ExampleGameSettings> {

    public ExampleGame() {
        super("ExampleGame", new ExampleGameSettings(GameSizeTemplate.FFA));
    }

    @Override
    public ExampleGamePlayer defaultGamePlayer(UUID uuid, boolean spectator) {
        return new ExampleGamePlayer(uuid, spectator);
    }

}