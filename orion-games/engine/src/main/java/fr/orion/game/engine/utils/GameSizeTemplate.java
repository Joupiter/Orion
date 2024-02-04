package fr.orion.game.engine.utils;

import fr.orion.game.engine.GameSize;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GameSizeTemplate {

    FFA (new GameSize("ffa", 0, 999, 0, 0)),
    SIZE_1V1 (new GameSize("1vs1", 2, 2, 2, 1)),
    SIZE_2V2 (new GameSize("2vs2", 2, 4, 2, 2)),
    SIZE_5V5 (new GameSize("5vs5", 5, 10, 2, 5)),
    SIZE_10V10 (new GameSize("10vs10", 10, 20, 2, 10));

    private final GameSize gameSize;

}