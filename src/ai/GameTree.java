package ai;

import util.MyTurnComparator;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class GameTree {

    // Has to be > 4, but must perform < 3s
    private final static int TREE_DEPTH = 5;

    private GameState currentState;

    private static Comparator<GameState> comparator = new MyTurnComparator();
    private static PriorityQueue<GameState> myTurns = new PriorityQueue<GameState>(1, comparator);
    private static Map<Integer, GameState> enemyTurns = new HashMap<Integer, GameState>();

    /**
     * Generating a Game Tree to make the decision for the next turn. If the tree depth is odd, than only the expanded
     * game state with the least heuristic will be added to the expanded list.
     * @param currentState
     */
    public GameTree(GameState currentState){
        this.currentState = currentState;
        GameState stateToExpand = currentState;
        GameState worstCurrentPossibleGameState = currentState;
        for(int i = 0; i < TREE_DEPTH; i++) {
            for(int j = 1; j < 7; j++) {
                // TODO: if second player j + 6
                if(stateToExpand.getCells()[j].getBeans() != 0) {
                    GameState expandedState = new GameState(stateToExpand.getCells());
                    expandedState.changeCurrentGamestate(j);
                    expandedState.setDepthInTree(i + 1);
                    if(i % 2 == 0) {
                        myTurns.add(expandedState);
                    } else {
                        if(enemyTurns.containsKey(i)) {
                            if(enemyTurns.get(i).getHeuristic() > expandedState.getHeuristic()) {
                                enemyTurns.put(i, expandedState);
                            }
                        }
                    }
                }
            }
            if(i % 2 == 0) {
                stateToExpand = myTurns.poll();
            } else {
                stateToExpand = enemyTurns.get(i);
            }
        }
    }
}
