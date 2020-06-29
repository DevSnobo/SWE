package application.example;

import application.logic.port.Observer;
import application.statemachine.port.State;

import java.util.ArrayList;
import java.util.List;

public class ExLogic implements ExModel {
    private List<Observer> observers = new ArrayList<>();
    private State          currentState;

    public void gameplayMethod1() {
        /* Wenn der Zustand passt, wird die Operation durchgeführt. */
        this.inform();
    }

    @Override
    public void attach(Observer obs) {
        observers.add(obs);
    }

    @Override
    public void detach(Observer obs) {
        observers.remove(obs);
    }

    private void inform() {
        this.observers.forEach(obs -> obs.update(this.currentState));
    }
}
