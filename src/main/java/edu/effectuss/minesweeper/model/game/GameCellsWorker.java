package edu.effectuss.minesweeper.model.game;

import edu.effectuss.minesweeper.model.game.impl.Coordinate;

public interface GameCellsWorker {
    void openCell(Coordinate coordinate);

    void openCellsAroundNumber(Coordinate coordinate);

    void toggleFlag(Coordinate coordinate);
}
