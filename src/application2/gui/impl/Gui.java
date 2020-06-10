package application2.gui.impl;

import application2.gui.impl.controller.Controller;
import application2.gui.port.Ui;
import application2.logic.port.GameAccessPort;
import application2.logic.port.MVCPort;
import application2.moveturn.impl.Board;
import application2.moveturn.impl.Figure;
import application2.moveturn.impl.Player;
import application2.statemachine.port.Observer;
import application2.statemachine.port.State;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.io.PrintStream;
import java.util.List;

public class Gui implements Ui, Observer {


    private State      state;
    private JFrame         mainFrame;
    private GameAccessPort gameAccessPort;
    private Controller     controller;
    private MVCPort    mvcPort;
    private JButton    buttonDice;
    private JButton    buttonMove;

    private Player       dataPlayer;
    private List<Figure> dataFigures;

    private State prevState;


    public Gui(GameAccessPort gameAccessPort, MVCPort mvcPort) {
        this.gameAccessPort = gameAccessPort;
        this.mvcPort = mvcPort;
        this.mvcPort.subject().attach(this);
        this.controller = new Controller(this);
    }

    public JFrame getMainFrame() {
        return this.mainFrame;
    }

    public void createFrame() {
        //Lambda runnable
        EventQueue.invokeLater(() -> {
            JFrame frame = new JFrame("Ich weiß was, was du nicht weißt!");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            JPanel panel = new JPanel();
            this.initLookAndFeel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.setOpaque(true);

            JTextArea textArea = new JTextArea(40, 100);
            textArea.setWrapStyleWord(true);
            textArea.setEditable(false);
            textArea.setFont(Font.getFont(Font.SANS_SERIF));
            OutputHandler taos = new OutputHandler(textArea, 60);
            PrintStream   ps   = new PrintStream(taos);
            System.setOut(ps);

            JScrollPane scroller = new JScrollPane(textArea);
            scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

            JPanel inputpanel = new JPanel();
            inputpanel.setLayout(new FlowLayout());
            JButton buttonDice = new JButton("WUERFELN");
            JButton buttonMove = new JButton("BEWEGEN");
            buttonDice.setActionCommand("dice");
            buttonMove.setActionCommand("move");
            buttonDice.addActionListener(this.controller);
            buttonMove.addActionListener(this.controller);
            buttonDice.setEnabled(true);
            buttonMove.setEnabled(false);

            DefaultCaret caret = (DefaultCaret) textArea.getCaret();
            caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

            panel.add(scroller);
            panel.setOpaque(true);
            inputpanel.add(buttonDice);
            inputpanel.add(buttonMove);
            panel.add(inputpanel);
            frame.add(new JLabel("Ich weiss was, was du nicht weisst!"), BorderLayout.NORTH);
            frame.getContentPane().add(BorderLayout.CENTER, panel);
            frame.pack();
            frame.setLocationByPlatform(true);
            frame.setVisible(true);
            frame.setResizable(false);
            this.buttonMove = buttonMove;
            this.buttonDice = buttonDice;
            this.mainFrame = frame;

        });
    }

    public JButton getButtonDice() {
        return buttonDice;
    }

    public JButton getButtonMove() {
        return buttonMove;
    }

    public MVCPort getMvcPort() {
        return this.mvcPort;
    }

    @Override
    public void startEventLoop() {
        createFrame();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void update(State newState) {
        if (newState == null) {
            return;
        }
        this.prevState = this.state;
        this.state = newState;
        if (this.getData())
            this.display();
    }


    private boolean getData() {
        if (this.gameAccessPort.gameFunctions().getActivePlayer() != null) {
            this.dataPlayer = this.gameAccessPort.gameFunctions().getActivePlayer();
            this.dataFigures = this.gameAccessPort.gameFunctions().getAllFigures();
        }
        return this.dataPlayer != null;
    }

    private void display() {
        String output = "";

        if (this.state == State.S.BEGIN_TURN) {
            output += "Es ist jetzt Spieler " + dataPlayer.getColour() + " dran." + "\n";
        } else if (this.state == State.S.TPASSIVE) {
            if (this.prevState != State.S.TPASSIVE) {
                output += dataPlayer.getColour() + " muss 3 mal wuerfeln!" + "\n";
            } else {
                output += "Es wurde eine " + this.dataPlayer.getBoard().getDiceResult() + " gewuerfelt!";
            }

        } else if (this.state == State.S.TACTIVE) {
            output += "Spieler " + dataPlayer.getColour() + " muss wuerfeln." + "\n";
        } else if (this.state == State.S.PMOVE) {
            output += "Es wurde eine 6 gewuerfelt! \'BEWEGEN\' druecken um Figur auf Heimatfeld zu setzen!";
        } else if (this.state == State.S.AMOVE) {
            output += "Wuerfelergebnis: " + dataPlayer.getBoard().getDiceResult() + "\n";
            output += "Spieler " + dataPlayer.getColour() + " muss \'BEWEGEN\' druecken, um eine Figur zu waehlen!" + "\n";
        } else if (this.state == State.S.NEXTPLAYER) {
            if (this.prevState == State.S.TPASSIVE) {
                output += "Aus 3 Versuchen leider keine 6 gewuerfelt!\n";
            }
            if (this.prevState == State.S.PMOVE) {
                output += "Figur von Heimatfeld auf Startfeld gesetzt!\n";
            }
            if (this.prevState == State.S.AMOVE) {
                output += "Figur " + this.dataPlayer.getBoard().getLastMoved().getColour() + " " +
                        " von Feld " + this.getLastPosition() + " auf Feld " +
                        +this.dataPlayer.getBoard().getLastMoved().getPosition() + " gesetzt!\n";
            }

        } else if (this.state == State.S.USECASE) {
            output += "Es wurde eine Figur geschlagen! \n";
            output += "Spieler " + dataPlayer.getColour() + " schlaegt " + dataPlayer.getBoard().getQuestionedFigure().getColour() + "!\n";
            output += "Fragerunde durchfuehren!" + "\n";
        }

        System.out.println(output);
    }


    public GameAccessPort getGamePort() {
        return gameAccessPort;
    }

    private int getLastPosition() {
        int targetPos = this.dataPlayer.getBoard().getLastMoved().getPosition();
        int diceResult = this.dataPlayer.getBoard().getDiceResult();
        if (targetPos - diceResult < 0) {
            return Board.FIELD_COUNT + targetPos - diceResult;
        }
        return targetPos - diceResult;
    }

    private void initLookAndFeel() {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
