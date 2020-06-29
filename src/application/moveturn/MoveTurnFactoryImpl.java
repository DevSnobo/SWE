package application.moveturn;

import application.moveturn.impl.GameplayMethodsImpl;
import application.moveturn.impl.Player;
import application.moveturn.impl.Turn;
import application.moveturn.port.GameplayMethods;
import application.moveturn.port.GameProviderPort;
import application.statemachine.StateMachineFactory;
import application.statemachine.port.State;
import application.statemachine.port.StateMachine;
import application.statemachine.port.StateMachinePort;

import java.util.List;

class MoveTurnFactoryImpl implements MoveTurnFactory, GameProviderPort, GameplayMethods {

    private StateMachinePort    stateMachinePort = StateMachineFactory.FACTORY.stateMachinePort();
    private StateMachine        stateMachine;
    private GameplayMethodsImpl game;

    @Override
    public GameProviderPort gameProviderPort() {
        return this;
    }

    @Override
    public GameplayMethods gameplayMethods() {
        this.createGame();
        return this;
    }

    @Override
    public void initGame() {
        if (this.stateMachine.getState() != State.S.UNINITIALIZED) {
            return;
        }
        this.game.initGame();
    }

    @Override
    public void startGame() {
        if (this.stateMachine.getState() != State.S.INITIALIZED) {
            return;
        }
        this.game.startGame();
    }

    @Override
    public void rollDice() {
        if (this.stateMachine.getState() != State.S.BEGINNING_TURN) {
            return;
        }
        this.game.rollDice();
    }

    @Override
    public int getCurrentResult() {
        if (this.stateMachine.getState() != State.S.DICE_ROLLED) {
            return -1;
        }
        return game.getCurrentResult();
    }

    @Override
    public List<Turn> getCurrentTurnList() {
        if (this.stateMachine.getState() != State.S.DICE_ROLLED) {
            return null;
        }
        return this.game.getCurrentTurnList();
    }

    @Override
    public void selectTurn(int pos) {
        if (this.stateMachine.getState() != State.S.DICE_ROLLED) {
            return;
        }
        this.game.selectTurn(pos);
    }

    @Override
    public void move() {
        if (this.stateMachine.getState() != State.S.TURN_SELECTED) {
            return;
        }
        this.game.move();
    }

    @Override
    public Player getCurrentPlayer() {
        if (this.stateMachine.getState() == State.S.UNINITIALIZED || this.stateMachine.getState() == State.S.INITIALIZED) {
            return null;
        }
        return this.game.getCurrentPlayer();
    }

    private void createGame() {
        if (this.game == null) {
            stateMachine = stateMachinePort.stateMachine();
            this.game = new GameplayMethodsImpl(stateMachinePort);
        }
    }
}
