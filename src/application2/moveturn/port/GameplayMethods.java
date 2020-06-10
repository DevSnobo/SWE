package application2.moveturn.port;

import application2.moveturn.impl.Figure;
import application2.moveturn.impl.Player;

import java.util.List;

public interface GameplayMethods {
    void setNextPlayer();
    void rollDice();
    void startTurn();
    void initGame();
    void move(int pos);
    List<Figure> getAllFigures();
    Player getActivePlayer();
}
