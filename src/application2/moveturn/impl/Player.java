package application2.moveturn.impl;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private Colours        colour;
    private KnowledgeLevel knowledgeLevel;
    private List<Figure>   figures;
    private int            startPosition;
    private int            homePosCounter;
    private Board          board;
    private boolean        isActive;

    public Player(Colours colour, int startPosition, Board board) {
        this.board = board;
        this.colour = colour;
        this.knowledgeLevel = new KnowledgeLevel();
        this.startPosition = startPosition;
        this.homePosCounter = 3;
        this.initHomePosition(colour);
        this.isActive = false;

    }

    public int getStartPosition() {
        return startPosition;
    }


    public List<Figure> getFigures() {
        return figures;
    }

    public void setFigures(List<Figure> figures) {
        this.figures = figures;
    }

    public Colours getColour() {
        return colour;
    }

    public void setColour(Colours colour) {
        this.colour = colour;
    }

    public void incrementHomePosCounter() {
        homePosCounter++;
    }

    public void decrementHomePosCounter() {
        homePosCounter--;
    }

    public int getHomePosCounter() {
        return homePosCounter;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Board getBoard() {
        return board;
    }

    @Override
    public String toString() {
        return "Player{" +
                "colour=" + colour +
                ", knowledgeLevel=" + knowledgeLevel +
                ", figures=" + figures +
                ", startPosition=" + startPosition +
                ", homePosCounter=" + homePosCounter +
                ", board=" + board +
                ", isActive=" + isActive +
                '}';
    }

    private void initHomePosition(Colours colour) {
        this.figures = new ArrayList<>(3);
        for (int i = 0; i < 3; i++) {
            this.figures.add(new Figure(colour));
        }
    }
}
