package application.statemachine;

import application.statemachine.port.*;

public class StateMachineFactoryImpl implements StateMachineFactory, StateMachinePort, StateMachine, SubjectPort, Subject {

	@Override
	public void setState(State state) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public State getState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Subject subject() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SubjectPort subjectPort() {
		return this;
	}

	@Override
	public StateMachinePort stateMachinePort() {
		return this;
	}
}
