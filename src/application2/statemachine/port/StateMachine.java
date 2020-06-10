package application2.statemachine.port;

public interface StateMachine {

    public void setState(State state);

    public State getState();
}
