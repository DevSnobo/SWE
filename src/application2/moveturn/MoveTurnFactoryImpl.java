package application2.moveturn;

import application2.moveturn.impl.Figure;
import application2.moveturn.impl.GameplayMethodsImpl;
import application2.moveturn.impl.Player;
import application2.moveturn.port.GameplayMethods;
import application2.moveturn.port.GameProviderPort;
import application2.statemachine.StateMachineFactory;
import application2.statemachine.port.State;
import application2.statemachine.port.StateMachine;
import application2.statemachine.port.StateMachinePort;

import java.util.List;

class MoveTurnFactoryImpl implements MoveTurnFactory, GameProviderPort, GameplayMethods {

    private final StateMachinePort    stateMachinePort = StateMachineFactory.FACTORY.stateMachinePort();
    private       StateMachine        stateMachine;
    private       GameplayMethodsImpl game;

    private void createGame() {
        if (this.game == null) {
            this.stateMachine = this.stateMachinePort.stateMachine();
            this.game = new GameplayMethodsImpl(this.stateMachinePort);
        }
    }

    @Override
    public GameProviderPort gameProviderPort() {
        return this;
    }

    @Override
    public GameplayMethods gameFunctions() {
        this.createGame();
        return this;
    }

    @Override
    public void setNextPlayer() {
        if (this.stateMachine.getState() != State.S.NEXTPLAYER) return;
        this.game.setNextPlayer();

    }

    @Override
    public void rollDice() {
        if (!this.stateMachine.getState().isSubStateOf(State.S.THROW)) return;
        this.game.rollDice();
    }

    @Override
    public void startTurn() {
        if (this.stateMachine.getState() != State.S.BEGIN_TURN) return;
        this.game.startTurn();
    }

    @Override
    public void initGame() {
        if (this.stateMachine.getState() != State.S.BEGIN_TURN) return;
        this.game.initGame();
    }

    @Override
    public void move(int pos) {
        if (!this.stateMachine.getState().isSubStateOf(State.S.MOVE)) return;
        this.game.move(pos);
    }

    @Override
    public List<Figure> getAllFigures() {
        return this.game.getAllFigures();
    }

    @Override
    public Player getActivePlayer() {
        return this.game.getActivePlayer();
    }
}
