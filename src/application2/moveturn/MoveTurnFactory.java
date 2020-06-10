package application2.moveturn;

import application2.moveturn.port.GameProviderPort;

public interface MoveTurnFactory {

	MoveTurnFactory FACTORY = new MoveTurnFactoryImpl();

	GameProviderPort gameProviderPort();

}
