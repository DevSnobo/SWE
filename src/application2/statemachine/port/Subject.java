package application2.statemachine.port;

public interface Subject {

    public void attach(Observer obs);

    public void detach(Observer obs);

}
