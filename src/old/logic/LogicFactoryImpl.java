package old.logic;

import old.gui.GamePort;
import old.statemachine.StateMachineFactory;
import old.logic.port.MVCPort;
import old.statemachine.port.SubjectPort;

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
