package edu.effectuss.minesweeper.service.record.impl;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.effectuss.minesweeper.view.forms.GameLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
public class PlayerRecord {
    @Setter
    @JsonProperty("name")
    private String name;

    @Setter
    @JsonProperty("timeInSeconds")
    private int timeInSeconds;

    @JsonProperty("gameLevel")
    private GameLevel gameLevel;

    @JsonCreator
    public PlayerRecord(@JsonProperty("gameLevel") GameLevel gameLevel,
                        @JsonProperty("name") String name,
                        @JsonProperty("timeInSeconds") int timeInSeconds) {
        this.gameLevel = gameLevel;
        this.name = name;
        this.timeInSeconds = timeInSeconds;
    }
}
