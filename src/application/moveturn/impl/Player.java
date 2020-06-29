package application.moveturn.impl;

public class Player {

    private final Colour colour;
    private final String name;
    private final int startPosition;

    public Player(Colour colour, String name, int startPosition) {
        this.colour = colour;
        this.name = name;
        this.startPosition = startPosition;
    }

    public Colour getColour() {
        return colour;
    }

    public String getName() {
        return name;
    }

    public int getStartPosition() {
        return startPosition;
    }
}
