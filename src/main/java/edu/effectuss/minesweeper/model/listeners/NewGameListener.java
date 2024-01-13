package edu.effectuss.minesweeper.model.listeners;

import edu.effectuss.minesweeper.model.game.impl.DifficultyLevel;

@FunctionalInterface
public interface NewGameListener {
    void newGameStarted(DifficultyLevel difficultyLevel);
}
