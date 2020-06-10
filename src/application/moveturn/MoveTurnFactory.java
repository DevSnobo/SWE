package application.moveturn;

import application.moveturn.port.GameProviderPort;

public interface MoveTurnFactory {

	MoveTurnFactory FACTORY = new MoveTurnFactoryImpl();

	GameProviderPort gameProviderPort();

}
