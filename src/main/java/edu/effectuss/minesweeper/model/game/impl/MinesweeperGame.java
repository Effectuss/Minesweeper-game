package edu.effectuss.minesweeper.model.game.impl;

import edu.effectuss.minesweeper.model.cell.Cell;
import edu.effectuss.minesweeper.model.cell.CellState;
import edu.effectuss.minesweeper.model.game.GameCellsWorker;
import edu.effectuss.minesweeper.model.game.GameInitializer;
import edu.effectuss.minesweeper.model.listeners.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class MinesweeperGame implements GameCellsWorker, GameInitializer {
    private final List<CellUpdateListener> cellUpdateListeners = new ArrayList<>();
    private final List<GameLostListener> gameLostListeners = new ArrayList<>();
    private final List<GameWonListener> gameWonListeners = new ArrayList<>();
    private final List<NumberOfMineListener> numberOfMineListeners = new ArrayList<>();
    private final List<GameStartedListener> gameStartedListeners = new ArrayList<>();
    private final List<NewGameListener> newGameListeners = new ArrayList<>();

    private MineField mineField;
    private GameStatus gameStatus;
    @Getter
    private DifficultyLevel difficultyLevel;

    public MinesweeperGame() {
        initializeGame();
    }

    private void initializeGame() {
        gameStatus = GameStatus.NEW;
        difficultyLevel = DifficultyLevel.BEGINNER;
        mineField = new MineField(
                difficultyLevel.getRows(),
                difficultyLevel.getCols(),
                difficultyLevel.getNumberOfMines()
        );
    }

    @Override
    public void startNewGame(DifficultyLevel difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
        mineField = new MineField(
                difficultyLevel.getRows(),
                difficultyLevel.getCols(),
                difficultyLevel.getNumberOfMines()
        );
        gameStatus = GameStatus.NEW;
        notifyNewGameListeners();
        notifyNumberOfMineListeners();
    }

    @Override
    public void openCell(Coordinate coordinate) {
        if (!isGameInProgress()) {
            return;
        }

        if (gameStatus == GameStatus.NEW) {
            mineField.fillMineField(coordinate);
            gameStatus = GameStatus.RUNNING;
            notifyGameStartedListeners();
        }

        Cell cell = mineField.getCell(coordinate);

        if (cell.isOpened() || cell.isFlagged()) {
            return;
        }

        handleCellOpening(cell);
    }

    @Override
    public void openCellsAroundNumber(Coordinate coordinate) {
        if (!isGameInProgress()) {
            return;
        }

        Cell cell = mineField.getCell(coordinate);

        if (cell.isFlagged()) {
            return;
        }

        List<Cell> updatedCells = mineField.openAdjacentCellsIfEnoughFlags(coordinate);
        notifyCellUpdateListeners(updatedCells);

        checkGameStatusAfterMove();
    }

    @Override
    public void toggleFlag(Coordinate coordinate) {
        if (!isGameInProgress()) {
            return;
        }

        if (gameStatus == GameStatus.NEW) {
            notifyGameStartedListeners();
        }

        Cell cell = mineField.getCell(coordinate);

        if (cell.isOpened()) {
            return;
        }

        mineField.toggleFlag(coordinate);
        notifyCellUpdateListeners(cell);
        notifyNumberOfMineListeners();
    }

    private void handleCellOpening(Cell cell) {
        List<Cell> openedCells = mineField.openCell(cell.getCoordinate());
        notifyCellUpdateListeners(openedCells);
        notifyNumberOfMineListeners();
        checkGameStatusAfterMove();
    }

    private boolean isGameInProgress() {
        return !gameStatus.equals(GameStatus.WIN) && !gameStatus.equals(GameStatus.LOSE);
    }

    private void checkGameStatusAfterMove() {
        if (isGameWon()) {
            gameStatus = GameStatus.WIN;
            notifyGameWonListeners();
        } else if (mineField.isBombExploded()) {
            gameStatus = GameStatus.LOSE;
            notifyGameLostListeners(mineField.getField());
        }
    }

    private boolean isGameWon() {
        for (Cell cell : mineField.getField()) {
            if (!cell.isMine() && cell.getCellState() == CellState.CLOSED) {
                return false;
            }
        }
        return true;
    }

    public void addGameStartedListener(GameStartedListener listener) {
        gameStartedListeners.add(listener);
    }


    public void addNewGameListener(NewGameListener listener) {
        newGameListeners.add(listener);
    }

    public void addNumberOfMineListener(NumberOfMineListener listener) {
        numberOfMineListeners.add(listener);
    }

    public void addGameWonListener(GameWonListener listener) {
        gameWonListeners.add(listener);
    }

    public void addGameLostListener(GameLostListener listener) {
        gameLostListeners.add(listener);
    }

    public void addCellUpdateListener(CellUpdateListener listener) {
        cellUpdateListeners.add(listener);
    }

    private void notifyGameStartedListeners() {
        for (GameStartedListener listener : gameStartedListeners) {
            listener.gameStarted();
        }
    }

    private void notifyCellUpdateListeners(Cell cell) {
        for (CellUpdateListener listener : cellUpdateListeners) {
            listener.cellUpdated(cell);
        }
    }

    private void notifyNewGameListeners() {
        for (NewGameListener listener : newGameListeners) {
            listener.newGameStarted(difficultyLevel);
        }
    }

    private void notifyNumberOfMineListeners() {
        for (NumberOfMineListener listener : numberOfMineListeners) {
            listener.numberOfMineUpdated(mineField.getNumberOfFlags());
        }
    }

    private void notifyCellUpdateListeners(List<Cell> openedCells) {
        for (CellUpdateListener listener : cellUpdateListeners) {
            listener.cellsUpdated(openedCells);
        }
    }

    private void notifyGameWonListeners() {
        for (GameWonListener listener : gameWonListeners) {
            listener.gameWon();
        }
    }

    private void notifyGameLostListeners(List<Cell> minesList) {
        for (GameLostListener listener : gameLostListeners) {
            listener.gameLost(minesList);
        }
    }
}
