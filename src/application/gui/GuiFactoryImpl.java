package application.gui;

import application.gui.port.Ui;
import application.gui.port.UiPort;
import application.logic.LogicFactory;
import application.logic.port.GameAccessPort;
import application.logic.port.MVCPort;

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
