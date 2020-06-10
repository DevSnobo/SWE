package application2.moveturn.port;

import java.util.List;

public interface GameplayMethods {
    void initGame();
    void startTurn();
    void rollDice();
    void move(int pos);

    /**
     * setNextPlayer()
     * getActivePlayer()
     * getAllUnits()
     */
}
