package application.moveturn.port;

import application.moveturn.impl.Player;
import application.moveturn.impl.Turn;

import java.util.List;

public interface GameplayInfos {
    int getCurrentResult();
    List<Turn> getCurrentTurnList();
    Player getCurrentPlayer();
    List<String> getAllUnitsOnBoard();
    Turn getSelectedTurn();
}
