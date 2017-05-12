package gameModel;

/**
 * Enumeration for the current state of the game.
 *
 * Version 2.0:
 * - Removed WIN state and changed LOSE to GAME_OVER as the game has no specific win lose conditions anymore
 */
public enum GameState {
    PLAYING, GAME_OVER
}
