package application.moveturn.impl;

public class Unit {

    private final Colour colour;
    private final int    identifier;

    public Unit(Colour colour, int identifier) {
        this.colour = colour;
        this.identifier = identifier;
    }

    public Colour getColour() {
        return colour;
    }

    public String getName() {
        return colour.name().charAt(0) + String.valueOf(identifier);
    }
}
