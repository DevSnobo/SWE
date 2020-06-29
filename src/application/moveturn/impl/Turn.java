package application.moveturn.impl;

import java.util.Collection;
import java.util.List;

public abstract class Turn {

    protected Unit             unitToMove;
    protected List<Unit> moveTo;
    protected int              from;
    protected int              to;

    public Turn() {
    }

    @Override
    public String toString() {
        return "Turn representation is still WIP.";
    }

    public void execute() {
    }
}
