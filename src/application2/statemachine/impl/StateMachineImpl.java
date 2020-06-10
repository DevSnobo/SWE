package application2.statemachine.impl;

import application2.statemachine.port.Observer;
import application2.statemachine.port.State;
import application2.statemachine.port.StateMachine;
import application2.statemachine.port.Subject;

import java.util.ArrayList;
import java.util.List;

public class StateMachineImpl implements StateMachine, Subject {

    private List<Observer> observers = new ArrayList<>();


    private State currentState;


    public StateMachineImpl() {
        this.currentState = State.S.BEGIN_TURN;
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

    //notify
    @Override
    public void setState(State state) {
        this.currentState = state;
        this.observers.forEach(obs -> obs.update(state));
    }

}
