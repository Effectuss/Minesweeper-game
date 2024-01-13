package edu.effectuss.minesweeper.service.record.listener;

import edu.effectuss.minesweeper.service.record.impl.PlayerRecord;

@FunctionalInterface
public interface RecordUpdateListener {
    void playerRecordUpdated(PlayerRecord playerRecord);
}
