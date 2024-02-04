package fr.orion.game.engine.phase;

public interface GamePhase {

    void onStart();

    void onEnd();

    default void onCancel() {}

}