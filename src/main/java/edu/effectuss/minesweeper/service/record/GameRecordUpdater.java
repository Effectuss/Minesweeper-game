package edu.effectuss.minesweeper.service.record;

@FunctionalInterface
public interface GameRecordUpdater {
    void addRecord(String name);
}
