package graphics;

import desktop.KeyHandler;
import engine.GameEngine;

public enum MenuOption {
    NEW_GAME,
    LOAD_GAME,
    EXIT;

    public MenuOption chooseOption() {
        if (KeyHandler.optionNumber == -1) {
            KeyHandler.optionNumber = 2;
        }
        if (KeyHandler.optionNumber == 3) {
            KeyHandler.optionNumber = 0;
        }

        return switch (KeyHandler.optionNumber) {
            case 2 -> NEW_GAME;
            case 1 -> LOAD_GAME;
            case 0 -> EXIT;
            default -> throw new IllegalStateException("Unexpected value: " + KeyHandler.optionNumber);
        };
    }

    public void enterOption(int optionNumber) {
        switch (optionNumber) {
            case 2 -> newGame();
            case 1 -> loadGame();
            case 0 -> System.exit(0);
        }
    }

    private void loadGame() {
    }

    private void newGame() {
        GameEngine.playBackgroundMusic(5);
        GameEngine.gameState = State.PLAY;
    }
}
