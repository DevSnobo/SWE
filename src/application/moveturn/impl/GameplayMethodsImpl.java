package application.moveturn.impl;

import application.moveturn.port.GameplayInfos;
import application.moveturn.port.GameplayMethods;
import application.statemachine.port.State;
import application.statemachine.port.StateMachine;
import application.statemachine.port.StateMachinePort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;

public class GameplayMethodsImpl implements GameplayMethods, GameplayInfos {

    // Player, Board, StateMachine, Dice, Result, CurrentPlayer
    private final int PLAYER_COUNT = 4;

    private final StateMachine stateMachine;
    private final List<Turn>   currentTurnList = new ArrayList<>();
    private       Board        board;
    private       List<Player> players;
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
        board = Board.getInstance();
        players = board.getPlayers();
        dice = new Dice();

        stateMachine.setState(State.S.INITIALIZED);
    }

    @Override
    public void startGame() {
        Colour startColour = Colour.of(dice.roll(PLAYER_COUNT));

        Optional<Player> opt      = null;
        boolean          foundHim = false;
        while (!foundHim) {
            try {
                Thread.sleep(200);
                opt = players.stream().filter(player -> player.getColour() == startColour).findAny();
                if (opt.isPresent()) {
                    foundHim = true;
                }
            } catch (InterruptedException ignored) {
            }
        }

        currentPlayer = opt.get();
        board.setCurrentPlayer(currentPlayer);

        stateMachine.setState(State.S.BEGINNING_TURN);
    }

    @Override
    public void rollDice() {
        // roll dice, check if turns available, else next player and -> beginning_turn
        currentTurnList.clear();
        currentResult = 0;

        if (isHomeFull()) {
            int rollCount = 0;
            while (rollCount < 3) {
                currentResult = dice.roll();
                System.out.println("rolled: " + currentResult);
                rollCount++;

                if (currentResult == Dice.MAX_ROLL) {
                    //INFO: adding turn from home
                    currentTurnList.add(new MoveOutTurn(board));
                    break;
                }
            }
            if (currentTurnList.size() == 0) {
                //INFO: next player, if no turns available
                nextPlayer();
                stateMachine.setState(State.S.BEGINNING_TURN);
                return;
            }
            //INFO: execute turn immediately and end turn
            currentTurnList.get(0).execute();
            System.out.println("moved to start");
            nextPlayer();
            stateMachine.setState(State.S.BEGINNING_TURN);
            return;

        } else {
            currentResult = dice.roll();
            //INFO: adding turns from home
            if (currentResult == Dice.MAX_ROLL && canMoveOut()) {
                currentTurnList.add(new MoveOutTurn(board));
            }
            //INFO: adding normal turns
            for (int index = 0; index < Board.BOARD_LENGTH; index++) {
                if (board.getColourAtIndex(index) == currentPlayer.getColour()
                    && canMoveTo((index + currentResult) % Board.BOARD_LENGTH)) {
                    currentTurnList.add(
                            new MoveOnBoardTurn(board, index, (index + currentResult) % Board.BOARD_LENGTH));
                }
            }
        }
        stateMachine.setState(State.S.DICE_ROLLED);
    }

    @Override
    public void selectTurn(int pos) {
        if (pos < 0 || pos >= currentTurnList.size()) {
            return;
        }
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
    public int getCurrentResult() {
        return currentResult;
    }

    @Override
    public List<Turn> getCurrentTurnList() {
        return currentTurnList;
    }

    @Override
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    @Override
    public List<String> getAllUnitsOnBoard() {
        List<String> unitList = new ArrayList<>();

        for (int boardIndex = 0; boardIndex < Board.BOARD_LENGTH; boardIndex++) {
            if (board.getColourAtIndex(boardIndex) == Colour.NONE) {
                continue;
            }
            unitList.add(board.getBoardPositions().get(boardIndex).getName() + " at position: " + boardIndex);
        }
        return unitList;
    }

    @Override
    public Turn getSelectedTurn() {
        return selectedTurn;
    }

    private void nextPlayer() {
        switch (currentPlayer.getColour()) {
            case RED:
                board.setCurrentPlayer(
                        players.stream().filter(player -> player.getColour() == Colour.YELLOW).findFirst().get());
                break;
            case YELLOW:
                board.setCurrentPlayer(
                        players.stream().filter(player -> player.getColour() == Colour.GREEN).findFirst().get());
                break;
            case GREEN:
                board.setCurrentPlayer(
                        players.stream().filter(player -> player.getColour() == Colour.BLUE).findFirst().get());
                break;
            case BLUE:
                board.setCurrentPlayer(
                        players.stream().filter(player -> player.getColour() == Colour.RED).findFirst().get());
                break;
        }
        currentPlayer = board.getCurrentPlayer();
    }

    private boolean isHomeFull() {
        return board.getHome(currentPlayer.getColour()).size() == Board.UNIT_COUNT;
    }

    private boolean canMoveOut() {
        //TODO: change here for variant
        return board.getColourAtIndex(currentPlayer.getStartPosition()) != currentPlayer.getColour();
    }

    private boolean canMoveTo(int index) {
        //TODO: change here for variant
        return board.getColourAtIndex(index) != currentPlayer.getColour();
    }

    // GetAllUnits

}
