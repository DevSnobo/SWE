package application.gui;

import application.gui.impl.Gui;
import application.gui.port.Ui;
import application.gui.port.UiPort;
import application.logic.LogicFactory;
import application.logic.port.GameAccessPort;
import application.logic.port.MVCPort;

public class GuiFactoryImpl implements GuiFactory, UiPort, Ui {

    private final GameAccessPort gameAccessPort = LogicFactory.FACTORY.gameAccessPort();
    private final MVCPort        mvcPort        = LogicFactory.FACTORY.MVCPort();
    private Gui gui;

    private void createUI() {
        if (this.gui == null)
            this.gui = new Gui(this.gameAccessPort, this.mvcPort);
    }

    @Override
    public UiPort uiPort() {
        return this;
    }

    @Override
    public Ui ui() {
        createUI();
        return this;
    }

    @Override
    public void startEventLoop() {
        this.gui.startEventLoop();
        this.gui.getGameAccessPort().gameplayMethods().initGame();
    }
}
