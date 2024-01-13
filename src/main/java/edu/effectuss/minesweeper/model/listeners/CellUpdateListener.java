package edu.effectuss.minesweeper.model.listeners;

import edu.effectuss.minesweeper.model.cell.Cell;

import java.util.List;

public interface CellUpdateListener {
    void cellsUpdated(List<Cell> openedCells);

    void cellUpdated(Cell cell);
}
