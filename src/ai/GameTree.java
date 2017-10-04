package ai;

import util.MyTurnComparator;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class GameTree {

    // Has to be > 4, but must perform < 3s
    private final static int TREE_DEPTH = 16;

//    private GameState currentState;
    public int gameMove;
    private boolean player;
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
     * This constructor kickstarts the min/max or alpha/beta pruning.
     * We only call this method if it's our turn. so we will always start with the max method.
     * If you are not sure how alpha/beta pruning works, this is not the place to learn it.
     * @param currentState The current GameState for the current player.
     * @param player Tells me which player I am.
     */
    public GameTree(GameState currentState, boolean player){
        this.player = player;
        max(TREE_DEPTH, currentState,-1000, 1000);
    }

    /**
     * This method goes through each branch of a gameState recursively,
     * and gives back the highest heuristic value that his branches had.
     * It calls the min function, which calls the max function, and so on...
     * It stops if we get to our desired depth or if we have no possible turns.
     * The best turn for this branch will be saved in the attribute gameMove.
     * It's pretty much the standard alpha/beta pruning code on wikipedia.
     * @param depth The current depth, counting down.
     * @param currentState The board state of this node.
     * @param alpha The current maximum heuristic value that the process found.
     * @param beta The current minimum heuristic value that the process found.
     * @return The maximum heuristic value that this node found.
     */
    private int max(int depth, GameState currentState, int alpha, int beta){
        if(depth == 0 || currentState.amountPossibleTurns() == 0){
            //TODO: Insert a good heuristic for the currentState here.
            if(this.player){
                return currentState.getPlayerOnePoints();// - currentState.getPlayerTwoPoints();
            } else {
                return currentState.getPlayerTwoPoints();// - currentState.getPlayerOnePoints();
            }
//            return currentState.getHeuristic();
        }
        int maxWert = alpha;
        int j, buffer;
        //jMaker sets j to the first index that belongs to his side of the field
        //buffer is the last index on the players field.
        //TODO: Maybe we need a better check for the field side.
        j = jMaker(currentState);
        buffer = j + 6;
        //go through all branches here.
        while(j < buffer){
            if(currentState.getCells()[j].getBeans() != 0){
                GameState expandedState = new GameState(currentState.getCells());
                expandedState.setCurrentPlayer(!currentState.isCurrentPlayer());
                expandedState.changeCurrentGamestate(j+1);
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

    /**
     * Just like max a part of min/max or alpha/beta pruning.
     * Goes through all branches of his node and returns the lowest heursitic value that it found.
     * @param depth The current depth, counting down.
     * @param currentState The board state of this node.
     * @param alpha The current maximum heuristic value that the process found.
     * @param beta The current minimum heuristic value that the process found.
     * @return The minimum heuristic value that this node found.
     */
    private int min(int depth, GameState currentState, int alpha, int beta){
        if(depth == 0 || currentState.amountPossibleTurns() == 0){
            if(this.player){
                return currentState.getPlayerOnePoints();// - currentState.getPlayerTwoPoints();
            } else {
                return currentState.getPlayerTwoPoints();// - currentState.getPlayerOnePoints();
            }
        }
        int minWert = beta;
        int j, buffer;
        //jMaker sets j to the first index that belongs to the current players side of the field.
        //buffer is the last index on the players field.
        //TODO: Maybe we need a better check for the field side.
        j = jMaker(currentState);
        buffer = j + 6;
        while(j < buffer){
            if(currentState.getCells()[j].getBeans() != 0){

                GameState expandedState = new GameState(currentState.getCells());
                expandedState.setCurrentPlayer(!currentState.isCurrentPlayer());
                expandedState.changeCurrentGamestate(j+1);
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

    /**
     * @param currentState current gameState
     * @return Returns the first index of the current players field.
     */
    private int jMaker(GameState currentState){
        int j;
        if(currentState.isCurrentPlayer()){
            j = 0;
        } else {
            j = 6;
        }
        return j;
    }
    /*
I think that my implementation of alpha/beta pruning is somewhat right.
Maybe there are problems with some parts of it. maybe the heuristic must be improved.
I've started counting player points in each GameState.
Before doing anything to gameState, please define the current player with .setCurrentPlayer.

I made changes in BohnenspielAI, GameTree, GameState, and BeanCell.
BohnenspielAI:
I replaced the random turns with new GameTrees and their Index.
I added 2 final boolean values. The beginning player is playerOne, the second player is playerTwo.
Before calling .changeCurrentGamestate(), please set .setCurrentPlayer(boolean) with the above player values.
GameTree:
Just look at the javadoc, I implemented the alpha/beta pruning.
GameState:
I changed distributeBeansOverCurrentGameState to count the player points. I need the currentPlayer variable for this.
BeanCell:
gave collectBeans a return value of the collected beans to count them in GameState.
     */
}