package application2.moveturn.impl;

import java.util.ArrayList;
import java.util.List;

public class Board {

    public static final int          FIRST_FIELD = 0;
    public static final int          FIELD_COUNT = 48;
    private static      Board        board;
    private             int          diceResult;
    private             Figure       lastMoved;
    private             Figure       questionedFigure;
    private             List<Figure> fields;

    private Board() {
        this.fields = new ArrayList<>(FIELD_COUNT);
        for (int i = FIRST_FIELD; i < FIELD_COUNT; i++) {
            fields.add(i, null);
        }
        this.diceResult = 0;
        this.questionedFigure = null;
    }

    public static Board getInstance() {
        if (Board.board == null) {
            Board.board = new Board();
        }
        return Board.board;
    }

    public void setField(int fieldNr, Figure figure) {
        this.fields.set(fieldNr, figure);
    }

    public boolean checkField(int fieldNr) {
        if (!(fieldNr < FIELD_COUNT && fieldNr >= FIRST_FIELD))
            return false;
        return this.fields.get(fieldNr) != null;
    }

    public List<Figure> getFields() {
        return fields;
    }

    public List<Figure> getAllFigures() {
        List<Figure> result = new ArrayList<>();
        for (int i = 0; i < fields.size(); i++) {
            if (this.fields.get(i) != null)
                result.add(this.fields.get(i));
        }
        return result;
    }

    public int getDiceResult() {
        return diceResult;
    }

    public void setDiceResult(int diceResult) {
        this.diceResult = diceResult;
    }

    public Figure getQuestionedFigure() {
        return questionedFigure;
    }

    public void setQuestionedFigure(Figure questionedFigure) {
        this.questionedFigure = questionedFigure;
    }

    public Figure getLastMoved() {
        return lastMoved;
    }

    public void setLastMoved(Figure lastMoved) {
        this.lastMoved = lastMoved;
    }

    @Override
    public String toString() {
        return "Board{" +
               "diceResult=" + diceResult +
               ", questionedFigure=" + questionedFigure +
               ", fields=" + fields +
               '}';
    }
}
