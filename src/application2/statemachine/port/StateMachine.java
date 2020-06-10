package application2.statemachine.port;

public interface StateMachine {

    void setState(State state);

    State getState();
}
