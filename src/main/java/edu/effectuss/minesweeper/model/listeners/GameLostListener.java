package edu.effectuss.minesweeper.model.listeners;

import edu.effectuss.minesweeper.model.cell.Cell;

import java.util.List;

@FunctionalInterface
public interface GameLostListener {
    void gameLost(List<Cell> minesList);
}
