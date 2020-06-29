package application.moveturn.impl;

import java.util.List;

public class MoveOnBoardTurn extends Turn {

    /**
     * Creates a Turn that moves a Unit to a different position on the board.
     *
     * @param unitToMove Unit that is moved.
     * @param moveTo     Board the Unit is moved to.
     * @param from       Index on the board the Unit is moved from.
     * @param to         Index on the board the Unit is moved to.
     */
    public MoveOnBoardTurn(Unit unitToMove, List<Unit> moveTo, int from, int to) {
        this.unitToMove = unitToMove;
        this.moveTo = moveTo;
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return unitToMove.getName() + ": board(" + from + ") -> board(" + to + ")";
    }

    @Override
    public void execute() {
        //TODO: implement this
        //TODO: move other player's units back to their home/start
        super.execute();
    }
}
