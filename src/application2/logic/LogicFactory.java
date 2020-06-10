package application2.logic;

import application2.logic.port.GameAccessPort;
import application2.logic.port.MVCPort;

public interface LogicFactory {

    LogicFactory FACTORY = new LogicFactoryImpl();

    MVCPort MVCPort();

    GameAccessPort gameAccessPort();

}
