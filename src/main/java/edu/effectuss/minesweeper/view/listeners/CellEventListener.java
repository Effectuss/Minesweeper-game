package edu.effectuss.minesweeper.view.listeners;

public interface CellEventListener {
    void onLeftButtonMouseClick(int x, int y);

    void onRightButtonMouseClick(int x, int y);

    void onMiddleButtonMouseClick(int x, int y);
}
