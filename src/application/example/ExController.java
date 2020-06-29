package application.example;

import application.logic.port.Observer;
import application.statemachine.port.State;

public class ExController implements Observer {
    private ExView  myView;
    private ExModel myModel;

    ExController(ExView view, ExModel model) {
        this.myView = view;
        this.myModel = model;
        this.myModel.attach(this);
    }

    public void update(State newState) {/* ggf. etwas tun */}

    void doit() {
        this.myView.show("Type here:"); /* Eingabeaufforderung */
        String input = System.console().readLine();
        switch (input) { /* Eingabe */
            case "roll":
                // do something
                //this.myModel.sysOp();
                break;

            case "move":
                // do something
                //this.myView.stop();
                break;


            /*case *//* definierte Eingabe *//*          : this.myModel.sysOp(); break;

            case *//* definierte Eingabe für exit *//* : this.myView.stop(); break;*/

            default:
                ;
        }
    }
}