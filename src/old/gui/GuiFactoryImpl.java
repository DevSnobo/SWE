package old.gui;

import old.logic.LogicFactory;
import old.logic.port.MVCPort;

public class GuiFactoryImpl implements GuiFactory {

	private GamePort gamePort = LogicFactory.FACTORY.gamePort();
	private MVCPort  mvcPort  = LogicFactory.FACTORY.MVCPort();

	private Gui gui;
}
