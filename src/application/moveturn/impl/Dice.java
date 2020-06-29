package application.moveturn.impl;

import java.util.Random;

public class Dice {

    public static final  int    MAX_ROLL = 6;
    private static final Random random   = new Random();

    public int roll() {
        return random.nextInt(MAX_ROLL) + 1;
    }

    public int roll(int max) {
        return random.nextInt(max) + 1;
    }
}
