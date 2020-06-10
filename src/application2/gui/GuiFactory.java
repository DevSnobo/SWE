package application2.gui;

import application2.gui.port.UiPort;

public interface GuiFactory {

    GuiFactory FACTORY = new GuiFactoryImpl();
    UiPort uiPort();
}
