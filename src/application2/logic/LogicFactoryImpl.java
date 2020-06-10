package application2.logic;

import application2.logic.port.GameAccessPort;
import application2.logic.port.MVCPort;
import application2.moveturn.MoveTurnFactory;
import application2.moveturn.port.GameplayMethods;
import application2.moveturn.port.GameProviderPort;
import application2.statemachine.port.Subject;
import application2.statemachine.port.SubjectPort;

class LogicFactoryImpl implements LogicFactory, GameAccessPort, MVCPort {

    private SubjectPort      subjectPort      = StateMachineFactory.FACTORY.subjectPort();
    private GameProviderPort gameProviderPort = MoveTurnFactory.FACTORY.gameProviderPort();


    @Override
    public MVCPort MVCPort() {
        return this;
    }

    @Override
    public GameAccessPort gameAccessPort() {
        return this;
    }

    @Override
    public Subject subject() {
        return this.subjectPort.subject();
    }

    @Override
    public GameplayMethods gameplayMethods() {
        return this.gameProviderPort.gameplayMethods();
    }

}
