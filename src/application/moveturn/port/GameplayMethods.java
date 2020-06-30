package application.moveturn.port;

public interface GameplayMethods {
    void initGame();
    void startGame();
    void rollDice();
    void selectTurn(int pos);
    void move();
}
