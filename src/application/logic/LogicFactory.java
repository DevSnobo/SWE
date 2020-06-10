package application.logic;

import application.gui.GamePort;
import application.logic.port.MVCPort;

public interface LogicFactory {
	
	LogicFactory FACTORY = new LogicFactoryImpl();
	MVCPort MVCPort();
	GamePort gamePort();
	
	void rollDice();
	void moveUnit();
	
}
