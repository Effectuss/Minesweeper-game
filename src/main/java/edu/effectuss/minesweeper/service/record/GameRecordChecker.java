package edu.effectuss.minesweeper.service.record;

import edu.effectuss.minesweeper.view.forms.GameLevel;

@FunctionalInterface
public interface GameRecordChecker {
    void checkRecord(GameLevel gameLevel);
}
