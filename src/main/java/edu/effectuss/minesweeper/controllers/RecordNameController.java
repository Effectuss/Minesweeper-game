package edu.effectuss.minesweeper.controllers;


import edu.effectuss.minesweeper.service.record.GameRecordUpdater;
import edu.effectuss.minesweeper.view.listeners.RecordNameListener;

public class RecordNameController implements RecordNameListener {
    private final GameRecordUpdater gameRecordUpdater;

    public RecordNameController(GameRecordUpdater gameRecordUpdater) {
        this.gameRecordUpdater = gameRecordUpdater;
    }

    @Override
    public void onRecordNameEntered(String name) {
        gameRecordUpdater.addRecord(name);
    }
}
