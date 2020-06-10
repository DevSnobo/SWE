package application2.gui;

import application2.gui.port.Ui;
import application2.gui.port.UiPort;
import application2.logic.LogicFactory;
import application2.logic.port.GameAccessPort;
import application2.logic.port.MVCPort;

public class GuiFactoryImpl implements GuiFactory, UiPort, Ui {

    private final GameAccessPort gameAccessPort = LogicFactory.FACTORY.gameAccessPort();
    private final MVCPort        mvcPort        = LogicFactory.FACTORY.MVCPort();

    @Override
    public UiPort uiPort() {
        return null;
    }

    @Override
    public Ui ui() {
        return null;
    }

    @Override
    public void startEventLoop() {

    }
}
