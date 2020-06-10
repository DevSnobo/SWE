package application.logic;

import application.logic.port.GameAccessPort;
import application.logic.port.MVCPort;

public interface LogicFactory {

    LogicFactory FACTORY = new LogicFactoryImpl();

    MVCPort MVCPort();

    GameAccessPort gameAccessPort();

}
