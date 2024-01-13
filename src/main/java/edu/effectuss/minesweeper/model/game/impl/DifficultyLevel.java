package edu.effectuss.minesweeper.model.game.impl;

import lombok.Getter;

@Getter
public enum DifficultyLevel {
    BEGINNER(9, 9, 10),
    INTERMEDIATE(16, 16, 40),
    EXPERT(16, 30, 99);

    private final int rows;
    private final int cols;
    private final int numberOfMines;

    DifficultyLevel(int rows, int cols, int numberOfMines) {
        this.rows = rows;
        this.cols = cols;
        this.numberOfMines = numberOfMines;
    }
}
