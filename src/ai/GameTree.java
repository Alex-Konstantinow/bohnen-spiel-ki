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
    public int gameMove;
    private static Comparator<GameState> comparator = new MyTurnComparator();
    private static PriorityQueue<GameState> myTurns = new PriorityQueue<GameState>(1, comparator);
    private static Map<Integer, GameState> enemyTurns = new HashMap<Integer, GameState>();

    /**
     * Generating a Game Tree to make the decision for the next turn. If the tree depth is odd, than only the expanded
     * game state with the least heuristic will be added to the expanded list.
     * @param currentState
     */
//    public GameTree(GameState currentState){
//        this.currentState = currentState;
//        GameState stateToExpand = currentState;
//        GameState worstCurrentPossibleGameState = currentState;
//        for(int i = 0; i < TREE_DEPTH; i++) {
//            for(int j = 1; j < 7; j++) {
//                // TODO: if second player j + 6
//                if(stateToExpand.getCells()[j].getBeans() != 0) {
//                    GameState expandedState = new GameState(stateToExpand.getCells());
//                    expandedState.changeCurrentGamestate(j);
//                    expandedState.setDepthInTree(i + 1);
//                    if(i % 2 == 0) {
//                        myTurns.add(expandedState);
//                    } else {
//                        if(enemyTurns.containsKey(i)) {
//                            if(enemyTurns.get(i).getHeuristic() > expandedState.getHeuristic()) {
//                                enemyTurns.put(i, expandedState);
//                            }
//                        }
//                    }
//                }
//            }
//            if(i % 2 == 0) {
//                stateToExpand = myTurns.poll();
//            } else {
//                stateToExpand = enemyTurns.get(i);
//            }
//        }
//    }

    /**
     * We only call this constructor if we have to make a move now. And then we hope.
     * @param currentState The current GameState for the current player.
     * @param flag This variable was just added because I don't want to call the other constructor.
     */
    public GameTree(GameState currentState, boolean flag){
        max(TREE_DEPTH, currentState, -1000, 1000);
    }

    private int max(int depth, GameState currentState, int alpha, int beta){
        if(depth == 0 || currentState.amountPossibleTurns() == 0){
            return currentState.getHeuristic();
        }
        int maxWert = alpha;
        int j, buffer;
        j = jMaker(currentState);
        buffer = j + 6;
        while(j < buffer){
            if(currentState.getCells()[j].getBeans() != 0){
                GameState expandedState = new GameState(currentState.getCells());
                expandedState.changeCurrentGamestate(j+1);
                expandedState.setStartPlayer(!currentState.getStartPlayer());

                int wert = min(depth-1, expandedState, maxWert, beta);
                if(wert > maxWert){
                    maxWert = wert;
                    if(maxWert >= beta){
                        break;
                    }
                    if(depth == TREE_DEPTH){
                        gameMove = j;
                    }
                }
            }
            j++;
        }
        return maxWert;
    }

    private int min(int depth, GameState currentState, int alpha, int beta){
        if(depth == 0 || currentState.amountPossibleTurns() == 0){
            return currentState.getHeuristic();
        }
        int minWert = beta;
        int j, buffer;
        j = jMaker(currentState);
        buffer = j + 6;
        while(j < buffer){
            if(currentState.getCells()[j].getBeans() != 0){
                GameState expandedState = new GameState(currentState.getCells());
                expandedState.changeCurrentGamestate(j+1);
                expandedState.setStartPlayer(!currentState.getStartPlayer());
                int wert = max(depth-1, expandedState, alpha, minWert);
                if(wert < minWert){
                    minWert = wert;
                    if(minWert <= alpha){
                        break;
                    }
                }
            }
            j++;
        }
        return minWert;
    }

    private int jMaker(GameState currentState){
        int j;
        if(currentState.getStartPlayer()){
            j = 0;
        } else {
            j = 6;
        }
        return j;
    }
}