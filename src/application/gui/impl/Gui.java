package application.gui.impl;

import application.gui.port.Ui;
import application.logic.port.GameAccessPort;
import application.logic.port.MVCPort;
import application.logic.port.Observer;
import application.moveturn.impl.Player;
import application.statemachine.port.State;

public class Gui implements Ui, Observer {

    private GameAccessPort gameAccessPort;
    private MVCPort        mvcPort;
    private Controller     controller;
    private State          currentState;
    private boolean        running = true;

    public Gui(GameAccessPort accessPort, MVCPort mvcPort) {
        this.gameAccessPort = accessPort;
        this.mvcPort = mvcPort;
        this.mvcPort.subject().attach(this);
        this.controller = new Controller(this);

        accessPort.gameplayMethods().initGame();
    }

    public GameAccessPort getGameAccessPort() {
        return gameAccessPort;
    }

    public MVCPort getMvcPort() {
        return mvcPort;
    }

    @Override
    public void startEventLoop() {
        while (running) {
            //INFO: display current state of the game
            this.display();

            //INFO: process inputs, call methods and transition to states accordingly
            this.controller.processInput();
        }
        //print "exiting" or something?
    }

    @Override
    public void update(State newState) {
        if (newState == null) {
            return;
        }
        this.currentState = newState;
    }

    public void show(String text) {
        //TODO: implement correctly
        System.out.println(text);
    }

    public void stop() {
        this.running = false;
    }

    private void display() {
        //TODO: implement display of current board state
        System.out.println();
        Player currentPlayer = gameAccessPort.gameplayMethods().getCurrentPlayer();
        if (currentPlayer != null) {
            System.out.println("Current Player: " + currentPlayer.getName());
        }

        if (State.S.INITIALIZED.equals(currentState)) {
            System.out.println("State: " + State.S.INITIALIZED.name());
            System.out.println("type 'start' to start the game");

        } else if (State.S.BEGINNING_TURN.equals(currentState)) {
            System.out.println("State: " + State.S.BEGINNING_TURN.name());
            System.out.println("type 'roll'");

        } else if (State.S.DICE_ROLLED.equals(currentState)) {
            System.out.println("State: " + State.S.DICE_ROLLED.name());
            System.out.println("Current dice result: " + gameAccessPort.gameplayMethods().getCurrentResult());
            System.out.println("Available turns: " + gameAccessPort.gameplayMethods().getCurrentTurnList().size());

            System.out.println("type 'select <index>' - index being one turn out of the list shown");

        } else if (State.S.TURN_SELECTED.equals(currentState)) {
            // show something
            System.out.println("State: " + State.S.TURN_SELECTED.name());
        }
    }
}
