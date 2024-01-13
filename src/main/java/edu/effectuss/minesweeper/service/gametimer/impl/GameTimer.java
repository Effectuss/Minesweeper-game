package edu.effectuss.minesweeper.service.gametimer.impl;

import edu.effectuss.minesweeper.service.gametimer.TimerContract;
import edu.effectuss.minesweeper.service.gametimer.listener.TimerUpdateListener;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class GameTimer implements TimerContract {
    private final List<TimerUpdateListener> timerUpdateListeners = new ArrayList<>();
    private final Timer timer;
    private TimerTask timerTask;
    @Getter
    private int timeInSeconds;

    public GameTimer() {
        timer = new Timer();
        timeInSeconds = 0;
    }

    @Override
    public void stopTimer() {
        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
            timeInSeconds = 0;
        }
    }

    @Override
    public void startTimer() {
        if (timerTask == null) {
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    timeInSeconds++;
                    updateViewTimerValue(timeInSeconds);
                }
            };
            timer.scheduleAtFixedRate(timerTask, 0, 1000);
        }
    }

    public void addTimerUpdateListener(TimerUpdateListener listener) {
        timerUpdateListeners.add(listener);
    }

    private void updateViewTimerValue(int timeInSeconds) {
        for (TimerUpdateListener listener : timerUpdateListeners) {
            listener.timeUpdated(timeInSeconds);
        }
    }
}
