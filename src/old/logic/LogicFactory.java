package old.logic;

import old.gui.GamePort;
import old.logic.port.MVCPort;

public interface LogicFactory {
	
	LogicFactory FACTORY = new LogicFactoryImpl();
	MVCPort MVCPort();
	GamePort gamePort();
	
	void rollDice();
	void moveUnit();
	
}
