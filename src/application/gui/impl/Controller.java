package application.gui.impl;

import application.logic.port.MVCPort;
import application.logic.port.Observer;
import application.statemachine.port.State;

import java.util.Scanner;

public class Controller implements Observer {

    private Gui     gui;
    private State   currentState;
    private MVCPort mvcPort;

    private Scanner scanner;

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
        this.gui.show("Type here:"); /* Eingabeaufforderung */
        String input = scanner.nextLine();
        switch (input) { /* Eingabe */
            /*case "init":
                gui.getGameAccessPort().gameplayMethods().initGame();
                break;*/
            case "start":
                gui.getGameAccessPort().gameplayMethods().startGame();
                break;
            case "select":
                gui.getGameAccessPort().gameplayMethods().rollDice();
                break;
            case "roll":
                gui.getGameAccessPort().gameplayMethods().rollDice();
                break;
            case "move":
                gui.getGameAccessPort().gameplayMethods().selectTurn(0);
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
