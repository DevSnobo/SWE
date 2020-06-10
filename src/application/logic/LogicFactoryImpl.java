package application.logic;

import application.gui.GamePort;
import application.statemachine.StateMachineFactory;
import application.logic.port.MVCPort;
import application.statemachine.port.SubjectPort;

public class LogicFactoryImpl implements LogicFactory, MVCPort, GamePort {
	
	private SubjectPort subjectPort = StateMachineFactory.FACTORY.subjectPort();
	
	public void rollDice() {
		return;
	}
	
	public void moveUnit() {
		return;
	}

	@Override
	public MVCPort MVCPort() {
		return this;
	}

	@Override
	public GamePort gamePort() {
		return this;
	}
}
