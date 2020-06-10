package old.statemachine.port;

public interface StateMachine {
	void setState(State state);
	State getState();
}
