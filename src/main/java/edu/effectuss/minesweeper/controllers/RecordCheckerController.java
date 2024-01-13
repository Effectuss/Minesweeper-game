package edu.effectuss.minesweeper.controllers;

import edu.effectuss.minesweeper.service.record.GameRecordChecker;
import edu.effectuss.minesweeper.view.forms.GameLevel;
import edu.effectuss.minesweeper.view.listeners.RecordCheckerListener;

public class RecordCheckerController implements RecordCheckerListener {
    private final GameRecordChecker recordChecker;

    public RecordCheckerController(GameRecordChecker recordChecker) {
        this.recordChecker = recordChecker;
    }

    @Override
    public void checkRecord(GameLevel gameLevel) {
        recordChecker.checkRecord(gameLevel);
    }
}
