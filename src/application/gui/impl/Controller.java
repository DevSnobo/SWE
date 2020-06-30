package application.gui.impl;

import application.logic.port.MVCPort;
import application.logic.port.Observer;
import application.statemachine.port.State;

import java.util.Scanner;

public class Controller implements Observer {

    private final Gui   gui;
    private final MVCPort mvcPort;
    private final Scanner scanner;
    private       State   currentState;

    public Controller(Gui gui) {
        this.gui = gui;
        this.mvcPort = this.gui.getMvcPort();
        this.mvcPort.subject().attach(this);

        scanner = new Scanner(System.in);
    }

    @Override
    public void update(State newState) {
        if (newState == null) {
            return;
        }
        this.currentState = newState;
    }

    public void processInput() {
        String input = scanner.nextLine();
        String[] splits = input.split(" ", 2);

        switch (splits[0]) {
            case "start":
                gui.getGameAccessPort().gameplayMethods().startGame();
                break;

            case "roll":
                gui.getGameAccessPort().gameplayMethods().rollDice();
                break;

            case "select":
                try {
                    gui.getGameAccessPort().gameplayMethods().selectTurn(Integer.parseInt(splits[1]));
                } catch (Exception e) {
                    System.out.println("Unable to parse: " + splits[1] + " as String.");
                }
                break;

            case "move":
                gui.getGameAccessPort().gameplayMethods().move();
                break;

            case "board":
                gui.show(gui.getGameAccessPort().gameplayInfos().getAllUnitsOnBoard());
                break;

            case "help":
            case "?":
            case "wtf":
            case "what":
                gui.showHelp();
                break;

            case "exit":
            case "quit":
                gui.stop();
                break;

            default:
                System.out.println("Something went wrong");
        }
    }
}
