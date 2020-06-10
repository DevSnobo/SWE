package application.statemachine;

import application.statemachine.port.StateMachinePort;
import application.statemachine.port.SubjectPort;

public interface  StateMachineFactory {

	StateMachineFactory FACTORY = new StateMachineFactoryImpl();

	SubjectPort subjectPort();

	StateMachinePort stateMachinePort();
}
