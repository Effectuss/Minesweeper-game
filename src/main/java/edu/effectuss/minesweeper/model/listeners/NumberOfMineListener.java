package edu.effectuss.minesweeper.model.listeners;

@FunctionalInterface
public interface NumberOfMineListener {
    void numberOfMineUpdated(int numberOfMine);
}
