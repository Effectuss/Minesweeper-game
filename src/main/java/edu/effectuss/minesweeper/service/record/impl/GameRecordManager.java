package edu.effectuss.minesweeper.service.record.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.CollectionType;
import edu.effectuss.minesweeper.service.gametimer.listener.TimerUpdateListener;
import edu.effectuss.minesweeper.service.record.GameRecordChecker;
import edu.effectuss.minesweeper.service.record.GameRecordInitializer;
import edu.effectuss.minesweeper.service.record.GameRecordUpdater;
import edu.effectuss.minesweeper.service.record.listener.RecordAchievementListener;
import edu.effectuss.minesweeper.service.record.listener.RecordUpdateListener;
import edu.effectuss.minesweeper.view.forms.GameLevel;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class GameRecordManager implements TimerUpdateListener, GameRecordUpdater, GameRecordInitializer
        , GameRecordChecker {
    private static final String FILE_PATH = RecordConfig.getRecordFilePath();
    private static final String DEFAULT_NAME = "Unknown";
    private static final int DEFAULT_TIME_IN_SECONDS = 9999;
    private final List<RecordUpdateListener> recordUpdateListeners = new ArrayList<>();
    private final List<RecordAchievementListener> recordAchievementListeners = new ArrayList<>();
    private final List<PlayerRecord> playerRecords;
    private int gameSessionTimeInSeconds;
    private GameLevel gameLevel;

    public GameRecordManager() {
        playerRecords = loadRecords();

        if (playerRecords.isEmpty()) {
            addDefaultRecords();
            saveRecords();
        }
    }

    private List<PlayerRecord> loadRecords() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        File file = new File(FILE_PATH);
        if (file.exists()) {
            try {
                CollectionType listType = mapper.
                        getTypeFactory().
                        constructCollectionType(List.class, PlayerRecord.class);
                return mapper.readValue(file, listType);
            } catch (IOException e) {
                log.error("Failed to load records from file {}", FILE_PATH);
            }
        }
        return new ArrayList<>();
    }

    private void addDefaultRecords() {
        playerRecords.add(new PlayerRecord(GameLevel.NOVICE, DEFAULT_NAME, DEFAULT_TIME_IN_SECONDS));
        playerRecords.add(new PlayerRecord(GameLevel.MEDIUM, DEFAULT_NAME, DEFAULT_TIME_IN_SECONDS));
        playerRecords.add(new PlayerRecord(GameLevel.EXPERT, DEFAULT_NAME, DEFAULT_TIME_IN_SECONDS));
    }

    public void addRecordAchievementListener(RecordAchievementListener recordAchievementListener) {
        recordAchievementListeners.add(recordAchievementListener);
    }

    public void addRecordUpdateListener(RecordUpdateListener recordUpdateListener) {
        recordUpdateListeners.add(recordUpdateListener);
    }

    @Override
    public void initializeRecords() {
        for (PlayerRecord playerRecord : playerRecords) {
            notifyRecordUpdateListeners(playerRecord);
        }
    }

    @Override
    public void timeUpdated(int timeInSeconds) {
        this.gameSessionTimeInSeconds = timeInSeconds;
    }


    @Override
    public void checkRecord(GameLevel gameLevel) {
        this.gameLevel = gameLevel;
        if (isRecord(gameLevel)) {
            notifyRecordAchievementListeners();
        }
    }

    @Override
    public void addRecord(String name) {
        if (isRecord(gameLevel)) {
            PlayerRecord recordToUpdate = findRecordByGameLevel();

            if (recordToUpdate != null) {
                updateExistingRecord(recordToUpdate, name);
            } else {
                createNewRecord(name);
            }

            saveRecords();
            notifyRecordUpdateListeners(recordToUpdate);
        }
    }

    private boolean isRecord(GameLevel gameLevel) {
        for (PlayerRecord playerRecord : playerRecords) {
            if (playerRecord.getTimeInSeconds() > gameSessionTimeInSeconds &&
                    playerRecord.getGameLevel() == gameLevel) {
                return true;
            }
        }
        return false;
    }

    private PlayerRecord findRecordByGameLevel() {
        for (PlayerRecord playerRecord : playerRecords) {
            if (playerRecord.getGameLevel() == gameLevel) {
                return playerRecord;
            }
        }
        return null;
    }

    private void saveRecords() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        try {
            mapper.writeValue(new File(FILE_PATH), playerRecords);
        } catch (IOException e) {
            log.error("Failed to save records to file {}", FILE_PATH);
        }
    }

    private void updateExistingRecord(PlayerRecord recordToUpdate, String name) {
        recordToUpdate.setTimeInSeconds(gameSessionTimeInSeconds);
        recordToUpdate.setName(name);
    }

    private void createNewRecord(String name) {
        PlayerRecord playerRecord = new PlayerRecord(gameLevel, name, gameSessionTimeInSeconds);
        playerRecords.add(playerRecord);
    }

    private void notifyRecordAchievementListeners() {
        for (RecordAchievementListener recordAchievementListener : recordAchievementListeners) {
            recordAchievementListener.gameRecordAchieved();
        }
    }

    private void notifyRecordUpdateListeners(PlayerRecord playerRecord) {
        for (RecordUpdateListener recordUpdateListener : recordUpdateListeners) {
            recordUpdateListener.playerRecordUpdated(playerRecord);
        }
    }
}
