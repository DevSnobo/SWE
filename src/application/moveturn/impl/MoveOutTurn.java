package application.moveturn.impl;

import java.util.List;
import java.util.Queue;

public class MoveOutTurn extends Turn {

    private final Queue<Unit> moveFrom;

    /**
     * Creates a Turn from the player's home to the board.
     *
     * @param unitToMove Unit that is moved from it's home.
     * @param moveTo     Board the Unit is moved to.
     * @param to         Index on the board the Unit is moved to.
     */
    public MoveOutTurn(Unit unitToMove, Queue<Unit> moveFrom, List<Unit> moveTo, int to) {
        this.unitToMove = unitToMove;
        this.moveFrom = moveFrom;
        this.moveTo = moveTo;
        this.to = to;
    }

    @Override
    public String toString() {
        return unitToMove.getName() + ": home -> board(" + to + ")";
    }

    @Override
    public void execute() {
        //TODO: implement this
        //TODO: move other player's units back to their home/start
        super.execute();

        Unit moveThis = moveFrom.poll();

        if (moveTo.get(to) != null) {

        }

        moveTo.set(to, moveThis);

    }
}
