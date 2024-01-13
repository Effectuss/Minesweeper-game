package edu.effectuss.minesweeper.controllers;

import edu.effectuss.minesweeper.model.game.GameInitializer;
import edu.effectuss.minesweeper.model.game.impl.DifficultyLevel;
import edu.effectuss.minesweeper.view.forms.GameLevel;
import edu.effectuss.minesweeper.view.listeners.GameTypeListener;

public class GameTypeController implements GameTypeListener {
    private final GameInitializer gameInitializer;

    public GameTypeController(GameInitializer gameInitializer) {
        this.gameInitializer = gameInitializer;
    }

    @Override
    public void onGameTypeChanged(GameLevel gameLevel) {
        switch (gameLevel) {
            case NOVICE -> gameInitializer.startNewGame(DifficultyLevel.BEGINNER);
            case MEDIUM -> gameInitializer.startNewGame(DifficultyLevel.INTERMEDIATE);
            case EXPERT -> gameInitializer.startNewGame(DifficultyLevel.EXPERT);
        }
    }
}
