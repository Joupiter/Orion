package example;

import fr.orion.game.engine.GamePlayer;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ExampleGamePlayer extends GamePlayer {

    private int kills;

    public ExampleGamePlayer(UUID uuid, boolean spectator) {
        super(uuid, spectator);
    }

}