package application.statemachine;


import application.logic.port.MVCPort;
import application.logic.port.Observer;
import application.logic.port.Subject;
import application.statemachine.impl.StateMachineImpl;
import application.statemachine.port.*;

class StateMachineFactoryImpl implements StateMachineFactory, MVCPort, StateMachinePort, StateMachine, Subject {

    private StateMachineImpl stateMachine;

    private   void mkStateMachine() {
        if (this.stateMachine == null)
            this.stateMachine = new StateMachineImpl();
    }

    @Override
    public SubjectPort subjectPort() {
        return this;
    }

    @Override
    public StateMachinePort stateMachinePort() {
        return this;
    }


    @Override
    public StateMachine stateMachine() {
        this.mkStateMachine();
        return this;
    }

    @Override
    public Subject subject() {
        this.mkStateMachine();
        return this;
    }

    @Override
    public synchronized void attach(Observer obs) {
        this.stateMachine.attach(obs);
    }

    @Override
    public synchronized void detach(Observer obs) {
        this.stateMachine.detach(obs);
    }


    @Override
    public synchronized State getState() {
        return this.stateMachine.getState();
    }

    @Override
    public synchronized void setState(State state) {
        this.stateMachine.setState(state);
    }


}