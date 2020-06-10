package application2.moveturn.impl;

public class Figure {


    private Colours colour;
    private int     position;

    // -1 = Heimatfeld
    public Figure(Colours colour) {

        this.colour = colour;
        this.position = -1;
    }

    public Figure(Colours colour, int position) {
        if (position < Board.FIELD_COUNT && position >= Board.FIRST_FIELD) {
            this.colour = colour;
            this.position = position;
        }
    }

    public Colours getColour() {
        return colour;
    }

    public boolean changePosition(int diceResult) {
        if (diceResult < 6 && diceResult > 0) {
            this.position += diceResult;
            this.position %= Board.FIELD_COUNT;
            return true;
        }
        return false;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        if (position < Board.FIELD_COUNT && position >= Board.FIRST_FIELD) {
            this.position = position;
        }
    }

    @Override
    public String toString() {
        return "Figure{" +
                "colour=" + colour +
                ", position=" + position +
                '}';
    }
}
