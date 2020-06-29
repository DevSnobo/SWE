package application.moveturn.impl;

import java.util.*;

public class Board {

    public static final int UNIT_COUNT   = 3;
    public static final int BOARD_LENGTH = 48;

    private static Board instance;

    private final List<Unit> boardPositions = new ArrayList<>();

    private final Queue<Unit> homeRed    = new ArrayDeque<>();
    private final Queue<Unit> homeYellow = new ArrayDeque<>();
    private final Queue<Unit> homeGreen  = new ArrayDeque<>();
    private final Queue<Unit> homeBlue   = new ArrayDeque<>();

    private Board() {
        initBoardPositions();
        initPlayerHomes();
    }

    public static Board getInstance() {
        if (instance == null) {
            instance = new Board();
        }
        return instance;
    }

    public List<Unit> getBoardPositions() {
        return boardPositions;
    }

    public Queue<Unit> getHome(Colour colour) {
        if (colour == Colour.RED) {
            return homeRed;
        } else if (colour == Colour.YELLOW) {
            return homeYellow;
        } else if (colour == Colour.GREEN) {
            return homeGreen;
        } else {
            return homeBlue;
        }
    }

    public Colour getColourAtIndex(int index) {
        if (boardPositions.get(index) != null) {
            return boardPositions.get(index).getColour();
        }
        return Colour.NONE;
    }

    private void initBoardPositions() {
        for (int index = 0; index < BOARD_LENGTH; index++) {
            boardPositions.add(null);
        }
    }

    private void initPlayerHomes() {
        initHome(Colour.RED, homeRed);
        initHome(Colour.YELLOW, homeYellow);
        initHome(Colour.GREEN, homeGreen);
        initHome(Colour.BLUE, homeBlue);
    }

    private void initHome(Colour color, Queue<Unit> home) {
        for (int count = 0; count < UNIT_COUNT; count++) {
            home.add(new Unit(color, count));
        }
    }
}
