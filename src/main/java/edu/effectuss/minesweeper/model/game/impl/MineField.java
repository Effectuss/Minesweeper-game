package edu.effectuss.minesweeper.model.game.impl;

import edu.effectuss.minesweeper.model.cell.Cell;
import edu.effectuss.minesweeper.model.cell.CellState;
import edu.effectuss.minesweeper.model.cell.CellType;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
class MineField {
    private final int rows;
    private final int cols;
    private final int numberOfMines;
    private final List<Cell> field;
    private boolean bombExploded;
    private int numberOfFlags;

    public MineField(int rows, int cols, int numberOfMines) {
        this.rows = rows;
        this.cols = cols;
        this.numberOfMines = numberOfMines;
        numberOfFlags = numberOfMines;
        field = new ArrayList<>(rows * cols);
        initField();
    }

    public void setCell(Cell cell) {
        field.set(cell.getCoordinate().x() + cell.getCoordinate().y() * cols, cell);
    }

    public Cell getCell(Coordinate coordinate) {
        return field.get(coordinate.x() + coordinate.y() * cols);
    }

    public void fillMineField(Coordinate firstClick) {
        placeMines(firstClick);
        placeNumbersAroundMines();
    }

    private void placeMines(Coordinate firstClick) {
        int minesPlaced = 0;

        while (minesPlaced < numberOfMines) {
            Coordinate randomCoordinate = Coordinate.generateRandomCoordinate(cols, rows);

            if (!getCell(randomCoordinate).isMine() && !randomCoordinate.equals(firstClick)) {
                Cell mineCell = new Cell(randomCoordinate, true);
                setCell(mineCell);
                minesPlaced++;
            }
        }
    }

    private void placeNumbersAroundMines() {
        for (Cell cell : field) {
            cell.setMinesAround(countMinesAround(cell));
        }
    }

    private int countMinesAround(Cell cell) {
        int minesCount = 0;
        int row = cell.getCoordinate().y();
        int col = cell.getCoordinate().x();

        for (int i = row - 1; i <= row + 1; ++i) {
            for (int j = col - 1; j <= col + 1; ++j) {
                if (isWithinBounds(i, j) && field.get(i * cols + j).isMine()) {
                    ++minesCount;
                }
            }
        }

        if (cell.isMine()) {
            --minesCount;
        }

        return minesCount;
    }

    public void toggleFlag(Coordinate coordinate) {
        Cell cell = getCell(coordinate);

        if (cell.isClosed() && numberOfFlags != 0) {
            cell.setCellState(CellState.FLAGGED);
            cell.setCellType(CellType.FLAGGED);
            --numberOfFlags;
        } else if (cell.isFlagged()) {
            cell.setCellState(CellState.CLOSED);
            cell.setCellType(CellType.UNDEFINED);
            ++numberOfFlags;
        }
    }

    public List<Cell> openCell(Coordinate coordinate) {
        List<Cell> openedCells = new ArrayList<>();
        Cell cell = getCell(coordinate);

        if (cell.isMine()) {
            bombExploded = true;
            changeCellTypeAfterLose();
            return openedCells;
        }

        if (cell.isClosed() || cell.isFlagged()) {
            cell.setCellState(CellState.OPENED);
            openedCells.add(cell);

            int minesAround = cell.getMinesAround();
            cell.setCellType(CellType.getCellTypeByNumberOfMine(minesAround));

            if (minesAround == 0) {
                openedCells.addAll(openAdjacentCells(cell, false));
            }
        }

        return openedCells;
    }


    public List<Cell> openAdjacentCellsIfEnoughFlags(Coordinate coordinate) {
        List<Cell> openedCells = new ArrayList<>();
        Cell cell = getCell(coordinate);

        if (cell.isOpened() && cell.getMinesAround() > 0) {
            int flaggedCount = countFlaggedAround(cell);

            if (flaggedCount == cell.getMinesAround()) {
                openedCells.addAll(openAdjacentCells(cell, true));
            }
        }

        return openedCells;
    }

    private int countFlaggedAround(Cell cell) {
        int flaggedCount = 0;
        int row = cell.getCoordinate().y();
        int col = cell.getCoordinate().x();

        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if (isWithinBounds(i, j) && field.get(i * cols + j).isFlagged()) {
                    flaggedCount++;
                }
            }
        }

        return flaggedCount;
    }

    private List<Cell> openAdjacentCells(Cell cell, boolean ignoreFlags) {
        List<Cell> openedCells = new ArrayList<>();
        int row = cell.getCoordinate().y();
        int col = cell.getCoordinate().x();

        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if (isWithinBounds(i, j) && shouldOpenAdjacentCell(i, j, ignoreFlags)) {
                    Cell adjacentCell = field.get(i * cols + j);

                    if (adjacentCell.isFlagged()) {
                        ++numberOfFlags;
                    }
                    openedCells.addAll(openCell(new Coordinate(j, i)));
                }
            }
        }

        return openedCells;
    }

    private boolean shouldOpenAdjacentCell(int i, int j, boolean ignoreFlags) {
        Cell adjacentCell = field.get(i * cols + j);
        return !(adjacentCell.isOpened() || (ignoreFlags && adjacentCell.isFlagged()));
    }

    private void changeCellTypeAfterLose() {
        for (Cell cell : field) {
            if (cell.isMine() && !cell.isFlagged()) {
                cell.setCellType(CellType.BOMB);
            }
        }
    }

    private boolean isWithinBounds(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }

    private void initField() {
        for (int y = 0; y < rows; ++y) {
            for (int x = 0; x < cols; ++x) {
                field.add(new Cell(new Coordinate(x, y), false));
            }
        }
    }
}
