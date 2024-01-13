package edu.effectuss.minesweeper.service.gametimer.listener;

@FunctionalInterface
public interface TimerUpdateListener {
    void timeUpdated(int timeInSeconds);
}
