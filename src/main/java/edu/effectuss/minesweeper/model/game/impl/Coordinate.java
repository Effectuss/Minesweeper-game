package edu.effectuss.minesweeper.model.game.impl;

import java.util.Random;

public record Coordinate(int x, int y) {
    private static final Random RANDOM = new Random();

    public static Coordinate generateRandomCoordinate(int maxX, int maxY) {
        return new Coordinate(RANDOM.nextInt(maxX),
                RANDOM.nextInt(maxY));
    }
}
