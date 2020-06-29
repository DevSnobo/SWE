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
        return unitToMove.getName() + ": home -> board(" + to + ")";
    }

    @Override
    public void execute() {
        Unit moveThis = moveFrom.poll();

        //TODO: start duel here, but don't
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
        moveTo.set(to, moveThis);
    }
}
