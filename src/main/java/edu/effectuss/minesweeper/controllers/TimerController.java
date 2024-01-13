package edu.effectuss.minesweeper.controllers;

import edu.effectuss.minesweeper.service.gametimer.TimerContract;
import edu.effectuss.minesweeper.view.listeners.TimerListener;

public class TimerController implements TimerListener {

    private final TimerContract timer;

    public TimerController(TimerContract timer) {
        this.timer = timer;
    }


    @Override
    public void startTimer() {
        timer.startTimer();
    }

    @Override
    public void stopTimer() {
        timer.stopTimer();
    }
}
