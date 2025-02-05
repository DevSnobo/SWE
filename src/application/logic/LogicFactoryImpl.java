package application.logic;

import application.logic.port.GameAccessPort;
import application.logic.port.MVCPort;
import application.moveturn.MoveTurnFactory;
import application.moveturn.port.GameplayInfos;
import application.moveturn.port.GameplayMethods;
import application.moveturn.port.GameProviderPort;
import application.logic.port.Subject;
import application.statemachine.StateMachineFactory;
import application.statemachine.port.SubjectPort;

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

    @Override
    public GameplayInfos gameplayInfos() {
        return this.gameProviderPort.gameplayInfos();
    }
}
