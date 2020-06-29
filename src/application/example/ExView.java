package application.example;

import application.logic.port.Observer;
import application.statemachine.port.State;

public class ExView implements Observer {

    private State        currentState;
    private ExController controller;
    private ExModel      model;
    private boolean      running = true;

    public ExView(ExModel model) {
        this.model = model;
        this.model.attach(this);
        this.controller = new ExController(this, model);
    }

    public void update(State newState) {
        this.currentState = newState;
    }

    /* Situation darstellen. */
    void display() {

    }

    /* Anweisungen des Controllers darstellen. */
    void show(String text) {
        //asd
    }

    void stop() {
        this.running = false;
    }

    public void startEventLoop() {
        while (running) {
            this.display();

            this.controller.doit();
        }
    }
}
