package graphics;

import desktop.KeyManager;
import engine.GameEngine;

public enum MenuOption {
    NEW_GAME,
    LOAD_GAME,
    EXIT;

    public MenuOption chooseOption() {
        if (KeyManager.optionNumber == -1) {
            KeyManager.optionNumber = 2;
        }
        if (KeyManager.optionNumber == 3) {
            KeyManager.optionNumber = 0;
        }

        return switch (KeyManager.optionNumber) {
            case 2 -> NEW_GAME;
            case 1 -> LOAD_GAME;
            case 0 -> EXIT;
            default -> throw new IllegalStateException("Unexpected value: " + KeyManager.optionNumber);
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
