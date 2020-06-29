package application.moveturn.impl;

import application.moveturn.port.GameplayMethods;
import application.statemachine.port.State;
import application.statemachine.port.StateMachine;
import application.statemachine.port.StateMachinePort;

import java.net.CookieHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

public class GameplayMethodsImpl implements GameplayMethods {

    // Player, Board, StateMachine, Dice, Result, CurrentPlayer
    private final int PLAYER_COUNT = 4;

    private final StateMachine stateMachine;
    private final List<Player> players         = new ArrayList<>();
    private final List<Turn>   currentTurnList = new ArrayList<>();
    private       Board        gameBoard;
    private       Player       currentPlayer   = null;
    private       Dice         dice;
    private       int          currentResult   = 0;

    private Turn selectedTurn;

    public GameplayMethodsImpl(StateMachinePort stateMachinePort) {
        stateMachine = stateMachinePort.stateMachine();
    }

    @Override
    public void initGame() {
        // initialize fields/board/whatever?
        players.add(new Player(Colour.RED, "Rot", 0));
        players.add(new Player(Colour.YELLOW, "Gelb", 12));
        players.add(new Player(Colour.GREEN, "Gruen", 24));
        players.add(new Player(Colour.BLUE, "Blau", 36));

        gameBoard = Board.getInstance();
        dice = new Dice();

        stateMachine.setState(State.S.INITIALIZED);
    }

    @Override
    public void startGame() {
        // randomize start player? do something or whatever
        Colour startColour = Colour.of(dice.roll(PLAYER_COUNT));
        currentPlayer = players.stream().filter(player -> player.getColour() == startColour).findAny().get();

        stateMachine.setState(State.S.BEGINNING_TURN);
    }

    @Override
    public void rollDice() {
        // roll dice, check if turns available, else next player and -> beginning_turn
        currentTurnList.clear();
        currentResult = 0;

        currentResult = dice.roll();

        // home full?
        if (isHomeFull()) {
            int rollCount = 1;

            System.out.println("rolled: " + currentResult);
            while (rollCount < 4) {
                if (currentResult == Dice.MAX_ROLL) {
                    // create Turn list
                    Queue<Unit> home = gameBoard.getHome(currentPlayer.getColour());
                    currentTurnList.add(
                            new MoveOutTurn(home.peek(), home,
                                            gameBoard.getBoardPositions(),
                                            currentPlayer.getStartPosition()));
                    break;
                }
                currentResult = dice.roll();
                System.out.println("rolled: " + currentResult);
                rollCount++;
            }
            if (currentTurnList.size() == 0) {
                nextPlayer();
                stateMachine.setState(State.S.BEGINNING_TURN);
                return;
            }
            currentTurnList.get(0).execute();
            System.out.println("moved to start");
            nextPlayer();
            stateMachine.setState(State.S.BEGINNING_TURN);
            return;

        } else {
            if (currentResult == Dice.MAX_ROLL && canMoveOut()) {
                //TODO: make sure other player's units are brought back to their home/start
                Queue<Unit> home = gameBoard.getHome(currentPlayer.getColour());
                currentTurnList.add(
                        new MoveOutTurn(home.peek(), home,
                                        gameBoard.getBoardPositions(),
                                        currentPlayer.getStartPosition()));
            }
            for (int index = 0; index < Board.BOARD_LENGTH; index++) {
                if (gameBoard.getColourAtIndex(index) == currentPlayer.getColour()) {
                    currentTurnList.add(
                            new MoveOnBoardTurn(gameBoard.getBoardPositions().get(index), gameBoard.getBoardPositions(),
                                                index, index + currentResult % Board.BOARD_LENGTH));
                }
            }
        }
        //TODO: add normal turns here
        stateMachine.setState(State.S.DICE_ROLLED);
    }

    @Override
    public int getCurrentResult() {
        return currentResult;
    }

    @Override
    public List<Turn> getCurrentTurnList() {
        return currentTurnList;
    }

    @Override
    public void selectTurn(int pos) {
        selectedTurn = currentTurnList.get(pos);
        stateMachine.setState(State.S.TURN_SELECTED);
    }

    @Override
    public void move() {
        selectedTurn.execute();
        selectedTurn = null;
        nextPlayer();
        stateMachine.setState(State.S.BEGINNING_TURN);
    }

    @Override
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Turn getSelectedTurn() {
        return selectedTurn;
    }

    private void nextPlayer() {
        switch (currentPlayer.getColour()) {
            case RED:
                currentPlayer =
                        players.stream().filter(player -> player.getColour() == Colour.YELLOW).findFirst().get();
                break;
            case YELLOW:
                currentPlayer = players.stream().filter(player -> player.getColour() == Colour.GREEN).findFirst().get();
                break;
            case GREEN:
                currentPlayer = players.stream().filter(player -> player.getColour() == Colour.BLUE).findFirst().get();
                break;
            case BLUE:
                currentPlayer = players.stream().filter(player -> player.getColour() == Colour.RED).findFirst().get();
                break;
        }
    }

    private boolean isHomeFull() {
        return gameBoard.getHome(currentPlayer.getColour()).size() == Board.UNIT_COUNT;
    }

    private boolean canMoveOut() {
        return gameBoard.getColourAtIndex(currentPlayer.getStartPosition()) != currentPlayer.getColour();
    }

    // GetAllPlayers

}
