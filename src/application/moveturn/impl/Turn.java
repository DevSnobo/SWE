package application.moveturn.impl;

import java.util.List;

public abstract class Turn {

    protected Board      board;
    protected Unit       unitToMove;
    protected List<Unit> moveTo;
    protected int from;
    protected int to;

    public Turn() {
    }

    @Override
    public String toString() {
        return "Turn representation is still WIP.";
    }

    public void execute() {
    }

    public void pownUnit() {
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
    }
}
