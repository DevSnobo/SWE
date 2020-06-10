package application2.gui.impl.controller;

import application2.gui.impl.Gui;
import application2.logic.port.MVCPort;
import application2.moveturn.impl.Figure;
import application2.statemachine.port.Observer;
import application2.statemachine.port.State;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Controller implements Observer, ActionListener {
    private Gui     gui;
    private State   state;
    private MVCPort mvcPort;

    public Controller(Gui gui) {
        this.gui = gui;
        this.mvcPort = this.gui.getMvcPort();
        this.mvcPort.subject().attach(this);
    }


    @Override
    public void update(State newState) {

        if (newState == null) {
            return;
        }
        this.state = newState;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String command = e.getActionCommand();

        if (command.equals("dice")) {
            this.gui.getGamePort().gameFunctions().rollDice();
            if (this.state == State.S.NEXTPLAYER) {
                this.gui.getGamePort().gameFunctions().setNextPlayer();
            }
        } else if (command.equals("move")) {
            String input = "-1";
            if (this.state == State.S.AMOVE) {
                List<Figure> figures = this.gui.getGamePort().gameFunctions().getAllFigures();
                String       output  = "";
                for (Figure f : figures) {
                    output += (f.toString() + "\n");
                }
                input = JOptionPane.showInputDialog(this.gui.getMainFrame(),
                        output, null);
            }
            if (this.state == State.S.AMOVE || this.state == State.S.PMOVE)
                this.gui.getGamePort().gameFunctions().move(Integer.parseInt(input));

        }
        this.gui.getButtonMove().setEnabled(false);
        this.gui.getButtonDice().setEnabled(false);

        if (this.state.isSubStateOf(State.S.MOVE)) {
            this.gui.getButtonMove().setEnabled(true);
            this.gui.getButtonDice().setEnabled(false);
        } else if (this.state.isSubStateOf(State.S.THROW)) {
            this.gui.getButtonMove().setEnabled(false);
            this.gui.getButtonDice().setEnabled(true);
        }


    }
}
