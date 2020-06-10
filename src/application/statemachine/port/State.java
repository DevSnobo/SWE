package application.statemachine.port;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface State {

    boolean isSubStateOf(State state);

    boolean isSuperStateOf(State state);

    enum S implements State {
        //BEGIN_TURN = check
        BEGIN_TURN, TACTIVE, TPASSIVE, THROW(TACTIVE, TPASSIVE), AMOVE, PMOVE, MOVE(AMOVE, PMOVE), NEXTPLAYER, USECASE;

        private List<State> subStates;

        private S(State... subS) {
            this.subStates = new ArrayList<>(Arrays.asList(subS));
        }

        @Override
        public boolean isSuperStateOf(State s) {
            boolean result = ((s == null) || (this == s));
            for (State state : this.subStates)
                result |= state.isSuperStateOf(s);
            return result;
        }

        @Override
        public boolean isSubStateOf(State state) {
            return (state == null) ? false : state.isSuperStateOf(this);
        }
    }

}