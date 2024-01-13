package edu.effectuss.minesweeper.model.game;

import edu.effectuss.minesweeper.model.game.impl.DifficultyLevel;

public interface GameInitializer {
    void startNewGame(DifficultyLevel difficultyLevel);

    DifficultyLevel getDifficultyLevel();
}
