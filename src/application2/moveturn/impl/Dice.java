package application2.moveturn.impl;

public class Dice {
    private static Dice dice;

    private Dice() {
    }

    public static Dice getIntance() {
        if (Dice.dice == null) {
            Dice.dice = new Dice();
        }
        return Dice.dice;
    }

    public int rollDice() {
        return (int) (Math.random() * 6) + 1;
    }
}
