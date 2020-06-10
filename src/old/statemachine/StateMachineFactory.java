package old.statemachine;

import old.statemachine.port.StateMachinePort;
import old.statemachine.port.SubjectPort;

public interface StateMachineFactory {

	StateMachineFactory FACTORY = new StateMachineFactoryImpl();
	SubjectPort subjectPort();
	StateMachinePort stateMachinePort();
}
