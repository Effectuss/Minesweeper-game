package edu.effectuss.minesweeper.controllers;

import edu.effectuss.minesweeper.model.game.GameInitializer;
import edu.effectuss.minesweeper.view.listeners.NewGameListener;

public class NewGameController implements NewGameListener {

    private final GameInitializer gameInitializer;

    public NewGameController(GameInitializer gameInitializer) {
        this.gameInitializer = gameInitializer;
    }

    @Override
    public void onNewGameClicked() {
        gameInitializer.startNewGame(gameInitializer.getDifficultyLevel());
    }
}
