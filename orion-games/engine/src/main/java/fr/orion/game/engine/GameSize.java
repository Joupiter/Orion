package fr.orion.game.engine;

import fr.orion.game.engine.utils.GameSizeTemplate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GameSize {

    private String name;
    private int minPlayer, maxPlayer, teamNeeded, teamMaxPlayer;

    public GameSize(GameSizeTemplate template) {
        this.name = template.getName();
        this.minPlayer = template.getMinPlayer();
        this.maxPlayer = template.getMaxPlayer();
        this.teamNeeded = template.getTeamNeeded();
        this.teamMaxPlayer = template.getTeamMaxPlayer();
    }

}