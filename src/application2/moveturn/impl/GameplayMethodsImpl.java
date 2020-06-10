package application2.moveturn.impl;

import application2.moveturn.port.GameplayMethods;
import application2.statemachine.port.State;
import application2.statemachine.port.StateMachine;
import application2.statemachine.port.StateMachinePort;

import java.util.ArrayList;
import java.util.List;

public class GameplayMethodsImpl implements GameplayMethods {

    private List<Player> players;
    private Board        board;
    private StateMachine stateMachine;
    private Dice         dice;

    private int diceResult;
    private int activePlayer;

    public GameplayMethodsImpl(StateMachinePort stateMachinePort) {
        this.stateMachine = stateMachinePort.stateMachine();
    }

    @Override
    public void initGame() {
        this.board = Board.getInstance();
        this.dice = Dice.getIntance();
        createPlayers();
        this.activePlayer = 0;
        this.players.get(activePlayer).setActive(true);
        this.stateMachine.setState(State.S.BEGIN_TURN);
        this.stateMachine.setState(State.S.TPASSIVE);
    }

    //Check + throw
    @Override
    public void startTurn() {
        if (players.get(activePlayer).getHomePosCounter() == 3) {
            this.stateMachine.setState(State.S.TPASSIVE);
            return;
        }
        this.stateMachine.setState(State.S.TACTIVE);
    }

    @Override
    public void rollDice() {
        int throwCounter = 0;
        if (this.stateMachine.getState() == State.S.TPASSIVE) {
            while (throwCounter < 3) {
                diceResult = dice.rollDice();
                this.board.setDiceResult(this.diceResult);
                this.stateMachine.setState(State.S.TPASSIVE);
                throwCounter++;
                if (diceResult == 6) {
                    this.stateMachine.setState(State.S.PMOVE);
                    return;
                }
                if (throwCounter > 2) {
                    this.stateMachine.setState(State.S.NEXTPLAYER);
                    return;
                }
            }
        }
        //Active state
        diceResult = dice.rollDice();
        this.board.setDiceResult(this.diceResult);
        if ((diceResult == 6 && players.get(activePlayer).getHomePosCounter() == 0)
            || (diceResult < 6 && players.get(activePlayer).getHomePosCounter() != 3)) {
            this.stateMachine.setState(State.S.AMOVE);
            return;
        }
        this.stateMachine.setState(State.S.PMOVE);
    }

    @Override
    public void setNextPlayer() {
        this.players.get(activePlayer).setActive(false);
        activePlayer = (activePlayer + 1) % 4;
        this.players.get(activePlayer).setActive(true);
        this.stateMachine.setState(State.S.BEGIN_TURN);
        startTurn();
    }

    @Override
    public void move(int pos) {
        if (diceResult == 6) {
            if (players.get(activePlayer).getHomePosCounter() != 0) {
                moveFigureToStart();
                return;
            }

        }
        //popup case
        moveFigure(pos);
    }

    @Override
    public List<Figure> getAllFigures() {
        return this.board.getAllFigures();
    }

    @Override
    public Player getActivePlayer() {
        if (this.players == null) {
            return null;
        }
        return this.players.get(activePlayer);
    }

    private void moveFigureToStart() {
        Player player        = this.players.get(activePlayer);
        int    startPosition = player.getStartPosition();
        if (this.board.checkField(startPosition)) {
            this.board.setQuestionedFigure(board.getFields().get(startPosition));
            this.stateMachine.setState(State.S.USECASE);
            return;
        }
        for (int i = 0; i < player.getFigures().size(); i++) {
            if (player.getFigures().get(i).getPosition() == -1) {
                this.board.setLastMoved(player.getFigures().get(i));
                player.getFigures().get(i).setPosition(startPosition);
                this.board.setLastMoved(player.getFigures().get(i));
                board.setField(startPosition, player.getFigures().get(i));
                player.decrementHomePosCounter();
                break;
            }
        }
        this.checkEndState();
    }

    //pos - Feld (0..48)
    private void moveFigure(int pos) {
        while (true) {
            if (this.board.checkField(pos)) {
                //access check
                if (this.players.get(activePlayer).getColour() == this.board.getFields().get(pos).getColour()) {
                    int newFigurePos = (pos + diceResult) % Board.FIELD_COUNT;
                    if (this.board.checkField(newFigurePos)) {
                        this.board.setQuestionedFigure(board.getFields().get(newFigurePos));
                        this.stateMachine.setState(State.S.USECASE);
                        return;
                    }
                    this.board.setField(newFigurePos, this.board.getFields().get(pos));
                    this.board.setField(pos, null);
                    this.board.getFields().get(newFigurePos).changePosition(diceResult);
                    this.board.setLastMoved(this.board.getFields().get(newFigurePos));
                    this.checkEndState();
                    return;
                }
                return;

            } else
                return;
        }

    }

    private void checkEndState() {
        if (this.stateMachine.getState() == State.S.USECASE) {
            return;
        }
        this.stateMachine.setState(State.S.NEXTPLAYER);
        setNextPlayer();
    }

    private void createPlayers() {
        this.players = new ArrayList<>(4);

        for (int i = 0; i < 4; i++) {
            players.add(new Player(Colours.values()[i], i * 12, board));
        }
    }

}
