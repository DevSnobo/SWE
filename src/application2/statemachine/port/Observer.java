package application2.statemachine.port;

public interface Observer {

	void update(State newState);
}
