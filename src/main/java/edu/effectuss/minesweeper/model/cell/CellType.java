package edu.effectuss.minesweeper.model.cell;

public enum CellType {
    ZERO,
    ONE,
    TWO,
    THREE,
    FOUR,
    FIVE,
    SIX,
    SEVEN,
    EIGHT,
    UNDEFINED,
    FLAGGED,
    BOMB;

    public static CellType getCellTypeByNumberOfMine(int numberOfMine) {
        if (numberOfMine < 0 || numberOfMine > 8) {
            return UNDEFINED;
        }
        return CellType.values()[numberOfMine];
    }
}
