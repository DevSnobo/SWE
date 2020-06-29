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
        return unitToMove.getName() + ": board(" + from + ") -> board(" + to + ")";
    }

    @Override
    public void execute() {
        if (moveTo.get(to) != null) {
            Unit toReset = moveTo.get(to);
            Player pownedPlayer = board.getPlayers().stream() //
                                       .filter(player -> player.getColour() == toReset.getColour()) //
                                       .findAny().get();

            if (board.getColourAtIndex(pownedPlayer.getStartPosition()) == pownedPlayer.getColour()) {
                // to home (own unit on start)
                board.getHome(pownedPlayer.getColour()).offer(toReset);
            } else {
                // to start (enemy or nothing on start)
                new MoveOnBoardTurn(board, to, pownedPlayer.getStartPosition()).execute();
            }
            moveTo.set(to, null);
        }

        Unit moveThis = unitToMove;
        moveTo.set(to, moveThis);
        moveTo.set(from, null);
    }
}
