package application.moveturn.port;

import application.moveturn.impl.Player;
import application.moveturn.impl.Turn;

import java.util.List;

public interface GameplayMethods {
    void initGame();
    void startGame();
    void rollDice();
    int getCurrentResult();
    List<Turn> getCurrentTurnList();
    void selectTurn(int pos);
    void move();

    Player getCurrentPlayer();

    /**
     * getAllUnits()
     */
}
