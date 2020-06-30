package application.moveturn.impl;

import java.util.List;

public class MoveOnBoardTurn extends Turn {

    /**
     * Creates a Turn that moves a Unit to a different position on the board.
     *
     * @param board Complete board.
     * @param from  Index on the board the Unit is moved from.
     * @param to    Index on the board the Unit is moved to.
     */
    public MoveOnBoardTurn(Board board, int from, int to) {
        this.board = board;
        this.unitToMove = board.getBoardPositions().get(from);
        this.moveTo = board.getBoardPositions();
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        String pownMarker = wouldPown() ? " ---> duel" : "";
        return unitToMove.getName() + ": board(" + from + ") -> board(" + to + ")" + pownMarker;
    }

    @Override
    public void execute() {
        super.pownUnit();

        Unit moveThis = unitToMove;
        moveTo.set(to, moveThis);
        moveTo.set(from, null);
    }
}
