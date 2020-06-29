package application.logic.port;

import application.statemachine.port.State;

public interface Observer {

	void update(State newState);
}
