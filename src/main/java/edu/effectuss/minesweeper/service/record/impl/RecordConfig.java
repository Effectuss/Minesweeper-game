package edu.effectuss.minesweeper.service.record.impl;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Slf4j
final class RecordConfig {
    private static final String CONFIG_FILE_PATH = "records.properties";
    private static final Properties properties;

    static {
        properties = new Properties();
        try (InputStream inputStream = RecordConfig.class.getClassLoader().
                getResourceAsStream(CONFIG_FILE_PATH)) {
            properties.load(inputStream);
        } catch (IOException e) {
            log.error("Failed to load config file {}", CONFIG_FILE_PATH);
        }
    }

    public static String getRecordFilePath() {
        return properties.getProperty("record_file_path");
    }

    private RecordConfig() {
    }
}
