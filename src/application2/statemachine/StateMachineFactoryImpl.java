package application2.statemachine;


import application2.statemachine.impl.StateMachineImpl;
import application2.statemachine.port.*;

class StateMachineFactoryImpl implements StateMachineFactory, SubjectPort, StateMachinePort, StateMachine, Subject {


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
    public void attach(Observer obs) {
        this.stateMachine.attach(obs);
    }

    @Override
    public void detach(Observer obs) {
        this.stateMachine.detach(obs);
    }


    @Override
    public State getState() {
        return this.stateMachine.getState();
    }

    @Override
    public void setState(State state) {
        this.stateMachine.setState(state);
    }


}
