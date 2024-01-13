package edu.effectuss.minesweeper;

import edu.effectuss.minesweeper.controllers.*;
import edu.effectuss.minesweeper.model.game.impl.MinesweeperGame;
import edu.effectuss.minesweeper.service.gametimer.impl.GameTimer;
import edu.effectuss.minesweeper.service.record.impl.GameRecordManager;
import edu.effectuss.minesweeper.view.MinesweeperView;

public class MinesweeperGameMediator {
    private final MinesweeperGame minesweeperGame;
    private final GameRecordManager gameRecordManager;
    private final GameTimer gameTimer;

    public MinesweeperGameMediator(MinesweeperGame minesweeperGame,
                                   GameRecordManager gameRecordManager, GameTimer gameTimer) {
        this.minesweeperGame = minesweeperGame;
        this.gameRecordManager = gameRecordManager;
        this.gameTimer = gameTimer;
    }

    public MinesweeperView createMinesweeperView() {
        CellEventController cellEventController = new CellEventController(minesweeperGame);
        NewGameController newGameController = new NewGameController(minesweeperGame);
        GameTypeController gameTypeController = new GameTypeController(minesweeperGame);
        RecordNameController recordNameController = new RecordNameController(gameRecordManager);
        RecordInitializeController recordInitializeController = new RecordInitializeController(gameRecordManager);
        TimerController timerController = new TimerController(gameTimer);
        RecordCheckerController recordCheckerController = new RecordCheckerController(gameRecordManager);

        return new MinesweeperView(
                cellEventController, newGameController,
                gameTypeController, recordNameController, recordInitializeController,
                timerController, recordCheckerController);
    }

    public void setupListeners(MinesweeperView minesweeperView) {
        gameTimer.addTimerUpdateListener(minesweeperView);
        gameTimer.addTimerUpdateListener(gameRecordManager);

        gameRecordManager.addRecordUpdateListener(minesweeperView);
        gameRecordManager.addRecordAchievementListener(minesweeperView);

        minesweeperGame.addGameStartedListener(minesweeperView);
        minesweeperGame.addCellUpdateListener(minesweeperView);
        minesweeperGame.addGameLostListener(minesweeperView);
        minesweeperGame.addGameWonListener(minesweeperView);
        minesweeperGame.addNumberOfMineListener(minesweeperView);
        minesweeperGame.addNewGameListener(minesweeperView);
    }
}
