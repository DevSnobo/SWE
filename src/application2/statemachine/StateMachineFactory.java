package application2.statemachine;

import application2.statemachine.port.StateMachinePort;
import application2.statemachine.port.SubjectPort;

public interface  StateMachineFactory {

	StateMachineFactory FACTORY = new StateMachineFactoryImpl();

	SubjectPort subjectPort();

	StateMachinePort stateMachinePort();
}
