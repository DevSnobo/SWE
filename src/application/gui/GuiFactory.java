package application.gui;

import application.gui.port.UiPort;

public interface GuiFactory {

    GuiFactory FACTORY = new GuiFactoryImpl();
    UiPort uiPort();
}
