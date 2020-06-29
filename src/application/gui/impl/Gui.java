package application.gui.impl;

import application.gui.port.Ui;
import application.logic.port.GameAccessPort;
import application.logic.port.MVCPort;
import application.logic.port.Observer;
import application.moveturn.impl.Player;
import application.moveturn.impl.Turn;
import application.statemachine.port.State;

import java.util.List;
import java.util.stream.Collectors;

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
        System.out.println("------------------------------");
        //TODO: implement display of current board state

        Player currentPlayer = gameAccessPort.gameplayMethods().getCurrentPlayer();
        if (currentPlayer != null) {
            System.out.println("Current Player: " + currentPlayer.getName());
        }

        if (State.S.INITIALIZED.equals(currentState)) {
            System.out.println("type 'start' to start the game");

        } else if (State.S.BEGINNING_TURN.equals(currentState)) {
            System.out.println("Options: 'roll'");

        } else if (State.S.DICE_ROLLED.equals(currentState)) {
            System.out.println("Current dice result: " + gameAccessPort.gameplayMethods().getCurrentResult());

            List<Turn>    tmp = gameAccessPort.gameplayMethods().getCurrentTurnList();
            StringBuilder sb  = new StringBuilder();

            sb.append("Available turns: (").append(tmp.size()).append(")\n");

            for (int index = 0; index < tmp.size(); index++) {
                sb.append(index).append(": ").append(tmp.get(index).toString()).append("\n");
            }
            System.out.println(sb.toString());

            System.out.println("type 'select <index>' - index being one turn out of the list shown");

        } else if (State.S.TURN_SELECTED.equals(currentState)) {
            // show something

        }
    }
}
