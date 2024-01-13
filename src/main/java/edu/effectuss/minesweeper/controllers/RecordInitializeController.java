package edu.effectuss.minesweeper.controllers;

import edu.effectuss.minesweeper.service.record.GameRecordInitializer;
import edu.effectuss.minesweeper.view.listeners.RecordInitializeListener;

public class RecordInitializeController implements RecordInitializeListener {
    private final GameRecordInitializer gameRecordInitializer;

    public RecordInitializeController(GameRecordInitializer gameRecordInitializer) {
        this.gameRecordInitializer = gameRecordInitializer;
    }

    @Override
    public void initializeRecords() {
        gameRecordInitializer.initializeRecords();
    }
}
