package edu.effectuss.minesweeper.controllers;

import edu.effectuss.minesweeper.model.game.GameCellsWorker;
import edu.effectuss.minesweeper.model.game.impl.Coordinate;
import edu.effectuss.minesweeper.view.listeners.CellEventListener;

public class CellEventController implements CellEventListener {
    private final GameCellsWorker gameCellsWorker;

    public CellEventController(GameCellsWorker gameCellsWorker) {
        this.gameCellsWorker = gameCellsWorker;
    }

    @Override
    public void onLeftButtonMouseClick(int x, int y) {
        gameCellsWorker.openCell(new Coordinate(x, y));
    }

    @Override
    public void onRightButtonMouseClick(int x, int y) {
        gameCellsWorker.toggleFlag(new Coordinate(x, y));
    }

    @Override
    public void onMiddleButtonMouseClick(int x, int y) {
        gameCellsWorker.openCellsAroundNumber(new Coordinate(x, y));
    }
}
