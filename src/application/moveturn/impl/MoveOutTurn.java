package application.moveturn.impl;

import java.util.Queue;

public class MoveOutTurn extends Turn {

    private final Queue<Unit> moveFrom;

    /**
     * Creates a Turn from the player's home to the board.
     *
     * @param board Complete board.
     */
    public MoveOutTurn(Board board) {
        this.board = board;
        this.moveFrom = board.getHome(board.getCurrentPlayer().getColour());
        this.unitToMove = moveFrom.peek();
        this.moveTo = board.getBoardPositions();
        this.to = board.getCurrentPlayer().getStartPosition();
    }

    @Override
    public String toString() {
        String pownMarker = wouldPown() ? " ---> duel" : "";
        return unitToMove.getName() + ": home -> board(" + to + ")" + pownMarker;
    }

    @Override
    public void execute() {
        Unit moveThis = moveFrom.poll();

        //TODO: start duel here, but don't
        super.pownUnit();
        moveTo.set(to, moveThis);
    }
}
