package application.moveturn;

import application.moveturn.impl.GameplayMethodsImpl;
import application.moveturn.port.GameplayMethods;
import application.moveturn.port.GameProviderPort;

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
