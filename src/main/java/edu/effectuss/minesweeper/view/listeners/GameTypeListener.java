package edu.effectuss.minesweeper.view.listeners;

import edu.effectuss.minesweeper.view.forms.GameLevel;

@FunctionalInterface
public interface GameTypeListener {
    void onGameTypeChanged(GameLevel gameLevel);
}
