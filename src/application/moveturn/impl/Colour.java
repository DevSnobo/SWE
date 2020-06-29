package application.moveturn.impl;

import java.time.Year;

public enum Colour {
    RED,
    YELLOW,
    GREEN,
    BLUE,
    NONE;


    public static Colour of(int index) {
        switch (index) {
            case 0:
                return RED;
            case 1:
                return YELLOW;
            case 2:
                return GREEN;
            case 3:
                return BLUE;
        }
        return null;
    }
}
