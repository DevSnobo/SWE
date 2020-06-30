package application.moveturn;

import application.moveturn.impl.GameplayMethodsImpl;
import application.moveturn.impl.Player;
import application.moveturn.impl.Turn;
import application.moveturn.port.GameplayInfos;
import application.moveturn.port.GameplayMethods;
import application.moveturn.port.GameProviderPort;
import application.statemachine.StateMachineFactory;
import application.statemachine.port.State;
import application.statemachine.port.StateMachine;
import application.statemachine.port.StateMachinePort;

import java.util.List;

class MoveTurnFactoryImpl implements MoveTurnFactory, GameProviderPort, GameplayMethods, GameplayInfos {

    private final StateMachinePort    stateMachinePort = StateMachineFactory.FACTORY.stateMachinePort();
    private       StateMachine        stateMachine;
    private       GameplayMethodsImpl game;

    @Override
    public GameProviderPort gameProviderPort() {
        return this;
    }

    @Override
    public GameplayMethods gameplayMethods() {
        createGame();
        return this;
    }

    @Override
    public GameplayInfos gameplayInfos() {
        createGame();
        return this;
    }

    @Override
    public void initGame() {
        if (stateMachine.getState() != State.S.UNINITIALIZED) {
            return;
        }
        game.initGame();
    }

    @Override
    public void startGame() {
        if (stateMachine.getState() != State.S.INITIALIZED) {
            return;
        }
        game.startGame();
    }

    @Override
    public void rollDice() {
        if (stateMachine.getState() != State.S.BEGINNING_TURN) {
            return;
        }
        game.rollDice();
    }

    @Override
    public void selectTurn(int pos) {
        if (stateMachine.getState() != State.S.DICE_ROLLED && stateMachine.getState() != State.S.TURN_SELECTED) {
            return;
        }
        game.selectTurn(pos);
    }

    @Override
    public void move() {
        if (stateMachine.getState() != State.S.TURN_SELECTED) {
            return;
        }
        game.move();
    }

    @Override
    public int getCurrentResult() {
        if (stateMachine.getState() != State.S.DICE_ROLLED) {
            return -1;
        }
        return game.getCurrentResult();
    }

    @Override
    public List<Turn> getCurrentTurnList() {
        if (stateMachine.getState() != State.S.DICE_ROLLED && stateMachine.getState() != State.S.TURN_SELECTED) {
            return null;
        }
        return game.getCurrentTurnList();
    }

    @Override
    public Player getCurrentPlayer() {
        if (stateMachine.getState() == State.S.UNINITIALIZED || stateMachine.getState() == State.S.INITIALIZED) {
            return null;
        }
        return game.getCurrentPlayer();
    }

    @Override
    public List<String> getAllUnitsOnBoard() {
        if (stateMachine.getState() != State.S.BEGINNING_TURN
            && stateMachine.getState() != State.S.DICE_ROLLED
            && stateMachine.getState() != State.S.TURN_SELECTED) {
            return null;
        }
        return game.getAllUnitsOnBoard();
    }

    @Override
    public Turn getSelectedTurn() {
        if (stateMachine.getState() != State.S.TURN_SELECTED) {
            return null;
        }
        return game.getSelectedTurn();
    }

    private void createGame() {
        if (game == null) {
            stateMachine = stateMachinePort.stateMachine();
            game = new GameplayMethodsImpl(stateMachinePort);
        }
    }
}
