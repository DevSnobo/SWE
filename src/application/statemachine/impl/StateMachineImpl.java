package application.statemachine.impl;

import application.logic.port.Observer;
import application.statemachine.port.State;
import application.statemachine.port.StateMachine;
import application.logic.port.Subject;

import java.util.ArrayList;
import java.util.List;

public class StateMachineImpl implements StateMachine, Subject {

    private List<Observer> observers = new ArrayList<>();
    private State currentState;

    public StateMachineImpl() {
        this.currentState = State.S.UNINITIALIZED;
    }

    @Override
    public void attach(Observer obs) {
        this.observers.add(obs);
        obs.update(this.currentState);
    }

    @Override
    public void detach(Observer obs) {
        this.observers.remove(obs);
    }

    @Override
    public State getState() {
        return this.currentState;
    }

    @Override
    public void setState(State state) {
        this.currentState = state;
        this.observers.forEach(obs -> obs.update(state));
    }
}
