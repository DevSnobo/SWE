package application2.moveturn;

import application2.moveturn.impl.GameplayMethodsImpl;
import application2.moveturn.port.GameplayMethods;
import application2.moveturn.port.GameProviderPort;
import application2.statemachine.port.State;
import application2.statemachine.port.StateMachinePort;

import java.util.List;

class MoveTurnFactoryImpl implements MoveTurnFactory, GameProviderPort, GameplayMethods {

    private       GameplayMethodsImpl game;

    private void createGame() {
        if (this.game == null) {
            this.game = new GameplayMethodsImpl();
        }
    }

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
        this.game.initGame();
    }

    @Override
    public void startTurn() {
        this.game.startTurn();
    }

    @Override
    public void rollDice() {
        this.game.rollDice();
    }

    @Override
    public void move(int pos) {
        this.game.move(pos);
    }
}
