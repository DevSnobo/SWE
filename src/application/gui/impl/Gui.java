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

    public void stop() {
        this.running = false;
    }

    public void showHelp() {
        StringBuilder sb = new StringBuilder();

        sb.append("Usage: [COMMAND]\n")
          .append("Play the game by typing one of the available commands.\n")
          .append("\n")
          .append("Commands:\n")
          .append("   start:          starts the game\n")
          .append("   roll:           rolls the dice, allowing you to continue your turn, if turns are available\n")
          .append("   select <index>: selects one of the available turns and prepares execution of it, \n")
          .append("                       <index> being a number between 1 and 3\n")
          .append("   move:           confirms selected turn and executes it\n")
          .append("   board:          shows the current state of the board\n")
          .append("   exit | quit:    exits the game and stops the program\n")
          .append("   help | ?:       shows this help message\n")
          .append("------------------------------");

        System.out.println(sb.toString());
    }

    public void show(List<String> boardInfos) {
        String infos = String.join("\n", boardInfos);

        System.out.println(infos);
    }

    private void display() {
        System.out.println("------------------------------");
        //TODO: implement display of current board state

        Player currentPlayer = gameAccessPort.gameplayInfos().getCurrentPlayer();
        if (currentPlayer != null) {
            System.out.println("Current Player: " + currentPlayer.getName());
        }

        if (State.S.INITIALIZED.equals(currentState)) {
            showHelp();

        } else if (State.S.BEGINNING_TURN.equals(currentState)) {
            System.out.println("Options: 'roll', 'board', 'help'");

        } else if (State.S.DICE_ROLLED.equals(currentState)) {
            System.out.println("Current dice result: " + gameAccessPort.gameplayInfos().getCurrentResult());
            printAvailableTurns();
            System.out.println("Options: 'select <index>', 'board', 'help'");

        } else if (State.S.TURN_SELECTED.equals(currentState)) {
            System.out.println(
                    "Currently selected turn: " + gameAccessPort.gameplayInfos().getSelectedTurn().toString());

            printAvailableTurns();
            System.out.println("Options: 'select <index>', 'move', 'board', 'help'");
        }
    }

    private void printAvailableTurns() {
        List<Turn>    tmp = gameAccessPort.gameplayInfos().getCurrentTurnList();
        StringBuilder sb  = new StringBuilder();

        sb.append("Available turns: (").append(tmp.size()).append(")\n");

        for (int index = 0; index < tmp.size(); index++) {
            sb.append(index).append(": ").append(tmp.get(index).toString()).append("\n");
        }
        System.out.println(sb.toString());
    }
}
