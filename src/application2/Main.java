package application2;

import application2.gui.GuiFactory;

public class Main {

    public static void main(String[] args) {
        Thread.currentThread().setName("Main");

        GuiFactory.FACTORY.uiPort().ui().startEventLoop();
    }

}
