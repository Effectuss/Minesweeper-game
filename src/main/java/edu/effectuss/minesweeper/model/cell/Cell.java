package edu.effectuss.minesweeper.model.cell;

import edu.effectuss.minesweeper.model.game.impl.Coordinate;
import lombok.Getter;
import lombok.Setter;

@Getter
public class Cell {
    private final Coordinate coordinate;
    @Setter
    private CellType cellType;
    @Setter
    private CellState cellState;
    private final boolean isMine;
    @Setter
    private int minesAround;

    public Cell(Coordinate coordinate, boolean isMine) {
        this.coordinate = coordinate;
        cellType = CellType.UNDEFINED;
        cellState = CellState.CLOSED;
        this.isMine = isMine;
    }

    public boolean isMine() {
        return isMine;
    }

    public boolean isOpened() {
        return cellState == CellState.OPENED;
    }

    public boolean isClosed() {
        return cellState == CellState.CLOSED;
    }

    public boolean isFlagged() {
        return cellState == CellState.FLAGGED;
    }

}
