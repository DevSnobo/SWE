package diagram;

import application.statemachine.port.State;

public interface Observer {
    void update(State newState);
}
