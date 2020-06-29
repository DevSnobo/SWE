package application;

import application.gui.GuiFactory;

public class Main {

    public static void main(String[] args) {
//        Thread.currentThread().setName("Main");

        GuiFactory.FACTORY.uiPort().ui().startEventLoop();
    }

}
