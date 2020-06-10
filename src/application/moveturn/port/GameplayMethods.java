package application.moveturn.port;

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
