package application2.gui;

import application2.gui.impl.Gui;
import application2.gui.port.Ui;
import application2.gui.port.UiPort;
import application2.logic.LogicFactory;
import application2.logic.port.GameAccessPort;
import application2.logic.port.MVCPort;

public class GuiFactoryImpl implements GuiFactory, UiPort, Ui {

    private GameAccessPort gameAccessPort = LogicFactory.FACTORY.gameAccessPort();
    private MVCPort        mvcPort        = LogicFactory.FACTORY.MVCPort();

    private Gui gui;

    private void createUI() {
        if (this.gui == null)
            this.gui = new Gui(this.gameAccessPort, this.mvcPort);
    }

    @Override
    public synchronized UiPort uiPort() {
        return this;
    }


    @Override
    public synchronized Ui ui() {
        this.createUI();
        return this;
    }

    @Override
    public synchronized void startEventLoop() {
        this.gui.startEventLoop();
        this.gui.getGamePort().gameFunctions().initGame();

    }


}
