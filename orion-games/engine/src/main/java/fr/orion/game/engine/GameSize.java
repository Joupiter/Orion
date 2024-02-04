package fr.orion.game.engine;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GameSize implements Cloneable {

    private String name;
    private int minPlayer, maxPlayer, teamNeeded, teamMaxPlayer;

    @Override
    public GameSize clone() {
        try {
            return (GameSize) super.clone();
        } catch (CloneNotSupportedException exception) {
            throw new AssertionError();
        }
    }

}