package edu.effectuss.minesweeper.view;


import edu.effectuss.minesweeper.controllers.*;
import edu.effectuss.minesweeper.model.cell.Cell;
import edu.effectuss.minesweeper.model.cell.CellType;
import edu.effectuss.minesweeper.model.game.impl.DifficultyLevel;
import edu.effectuss.minesweeper.model.listeners.*;
import edu.effectuss.minesweeper.service.gametimer.listener.TimerUpdateListener;
import edu.effectuss.minesweeper.service.record.impl.PlayerRecord;
import edu.effectuss.minesweeper.service.record.listener.RecordAchievementListener;
import edu.effectuss.minesweeper.service.record.listener.RecordUpdateListener;
import edu.effectuss.minesweeper.view.forms.*;
import edu.effectuss.minesweeper.view.listeners.RecordCheckerListener;
import edu.effectuss.minesweeper.view.listeners.RecordInitializeListener;
import edu.effectuss.minesweeper.view.listeners.TimerListener;

import java.util.List;

public class MinesweeperView implements CellUpdateListener, GameLostListener,
        GameWonListener, NumberOfMineListener, NewGameListener, TimerUpdateListener,
        RecordUpdateListener, RecordAchievementListener, GameStartedListener {
    private MainWindow mainWindow;
    private HighScoresWindow highScoresWindow;
    private SettingsWindow settingsWindow;
    private LoseWindow loseWindow;
    private WinWindow winWindow;
    private RecordsWindow recordsWindow;
    private RecordInitializeListener recordInitializeListener;
    private TimerListener timerListener;
    private RecordCheckerListener recordCheckerListener;

    public MinesweeperView(CellEventController cellEventListener, NewGameController newGameListener,
                           GameTypeController gameTypeController, RecordNameController recordNameController,
                           RecordInitializeController recordInitializeController, TimerController timerController,
                           RecordCheckerController recordCheckerController) {
        initializeWindows(cellEventListener, newGameListener, gameTypeController,
                recordNameController, recordInitializeController, timerController, recordCheckerController);
        createGameField(9, 9);
        setBombsCount(10);
    }

    private void initializeWindows(CellEventController cellEventListener, NewGameController newGameListener,
                                   GameTypeController gameTypeController, RecordNameController recordNameController,
                                   RecordInitializeController recordInitializeController, TimerController timerController,
                                   RecordCheckerController recordCheckerController) {
        mainWindow = new MainWindow();
        settingsWindow = new SettingsWindow(mainWindow);
        highScoresWindow = new HighScoresWindow(mainWindow);
        loseWindow = new LoseWindow(mainWindow);
        winWindow = new WinWindow(mainWindow);
        recordsWindow = new RecordsWindow(mainWindow);

        settingsWindow.setGameTypeListener(gameTypeController);

        mainWindow.setExitMenuAction(e -> mainWindow.dispose());
        mainWindow.setNewGameMenuAction(e -> newGameListener.onNewGameClicked());
        mainWindow.setCellListener(cellEventListener);

        loseWindow.setNewGameListener(e -> newGameListener.onNewGameClicked());
        loseWindow.setExitListener(e -> loseWindow.dispose());

        winWindow.setNewGameListener(e -> newGameListener.onNewGameClicked());
        winWindow.setExitListener(e -> winWindow.dispose());

        recordsWindow.setNameListener(recordNameController);

        recordInitializeListener = recordInitializeController;
        timerListener = timerController;
        recordCheckerListener = recordCheckerController;
    }


    public void show() {
        recordInitializeListener.initializeRecords();
        mainWindow.setSettingsMenuAction(e -> settingsWindow.setVisible(true));
        mainWindow.setHighScoresMenuAction(e -> highScoresWindow.setVisible(true));
        mainWindow.setVisible(true);
    }

    @Override
    public void cellsUpdated(List<Cell> openedCells) {
        for (Cell cell : openedCells) {
            mainWindow.setCellImage(cell.getCoordinate().x(), cell.getCoordinate().y()
                    , convertCellTypeToGameImage(cell.getCellType()));
        }

        mainWindow.repaint();
    }

    @Override
    public void cellUpdated(Cell cell) {
        mainWindow.setCellImage(cell.getCoordinate().x(), cell.getCoordinate().y()
                , convertCellTypeToGameImage(cell.getCellType()));
    }

    @Override
    public void gameLost(List<Cell> field) {
        timerListener.stopTimer();
        for (Cell cell : field) {
            mainWindow.setCellImage(cell.getCoordinate().x(), cell.getCoordinate().y(),
                    convertCellTypeToGameImage(cell.getCellType()));
        }
        loseWindow.setVisible(true);
    }

    @Override
    public void numberOfMineUpdated(int numberOfMine) {
        setBombsCount(numberOfMine);
    }

    @Override
    public void gameWon() {
        timerListener.stopTimer();
        recordCheckerListener.checkRecord(settingsWindow.getGameLevel());
        winWindow.setVisible(true);
    }

    @Override
    public void newGameStarted(DifficultyLevel difficultyLevel) {
        createGameField(difficultyLevel.getRows(), difficultyLevel.getCols());
    }

    private void createGameField(int rows, int cols) {
        timerListener.stopTimer();
        mainWindow.createGameField(rows, cols);
    }

    private void setBombsCount(int numberOfBombs) {
        mainWindow.setBombsCount(numberOfBombs);
    }

    private GameImage convertCellTypeToGameImage(CellType cellType) {
        return switch (cellType) {
            case ZERO -> GameImage.EMPTY;
            case ONE -> GameImage.NUM_1;
            case TWO -> GameImage.NUM_2;
            case THREE -> GameImage.NUM_3;
            case FOUR -> GameImage.NUM_4;
            case FIVE -> GameImage.NUM_5;
            case SIX -> GameImage.NUM_6;
            case SEVEN -> GameImage.NUM_7;
            case EIGHT -> GameImage.NUM_8;
            case UNDEFINED -> GameImage.CLOSED;
            case FLAGGED -> GameImage.MARKED;
            case BOMB -> GameImage.BOMB;
        };
    }

    @Override
    public void timeUpdated(int timeInSeconds) {
        mainWindow.setTimerValue(timeInSeconds);
    }

    @Override
    public void playerRecordUpdated(PlayerRecord playerRecord) {
        switch (playerRecord.getGameLevel()) {
            case NOVICE -> highScoresWindow.setNoviceRecord(playerRecord.getName(),
                    playerRecord.getTimeInSeconds());
            case MEDIUM -> highScoresWindow.setMediumRecord(playerRecord.getName(),
                    playerRecord.getTimeInSeconds());
            case EXPERT -> highScoresWindow.setExpertRecord(playerRecord.getName(),
                    playerRecord.getTimeInSeconds());
        }
    }

    @Override
    public void gameRecordAchieved() {
        recordsWindow.setVisible(true);
    }

    @Override
    public void gameStarted() {
        timerListener.startTimer();
    }
}
