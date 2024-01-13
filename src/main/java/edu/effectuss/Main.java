package edu.effectuss;

import edu.effectuss.minesweeper.MinesweeperGameMediator;
import edu.effectuss.minesweeper.model.game.impl.MinesweeperGame;
import edu.effectuss.minesweeper.service.gametimer.impl.GameTimer;
import edu.effectuss.minesweeper.service.record.impl.GameRecordManager;
import edu.effectuss.minesweeper.view.MinesweeperView;

public class Main {
    public static void main(String[] args) {
        MinesweeperGame minesweeperGame = new MinesweeperGame();
        GameTimer gameTimer = new GameTimer();
        GameRecordManager gameRecordManager = new GameRecordManager();

        MinesweeperGameMediator mediator = new MinesweeperGameMediator(
                minesweeperGame, gameRecordManager, gameTimer
        );

        MinesweeperView minesweeperView = mediator.createMinesweeperView();

        mediator.setupListeners(minesweeperView);

        minesweeperView.show();
    }

}