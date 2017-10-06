package ai;

import java.util.ArrayList;

public class GameTree {

    // Has to be > 4, but must perform < 3s
    private final static int TREE_DEPTH = 8;

    private int gameMove;
    private boolean firstPlayer;

    /**
     * Generating a Game Tree to make the decision for the next turn. If the tree depth is odd, than only the expanded
     * game state with the least heuristic will be added to the expanded list.
     *
     * @param currentState
     */
    public GameTree(GameState currentState) {
        max(TREE_DEPTH, currentState, -1000, 1000);
    }

    /**
     * This constructor kickstarts the min/max or alpha/beta pruning.
     * We only call this method if it's our turn. so we will always start with the max method.
     * If you are not sure how alpha/beta pruning works, this is not the place to learn it.
     *
     * @param currentState The current GameState for the current firstPlayer.
     * @param firstPlayer  Tells me which firstPlayer I am.
     */
    public GameTree(GameState currentState, boolean firstPlayer) {
        this.firstPlayer = firstPlayer;
        max(TREE_DEPTH, currentState, -100000000, 100000000);
    }

    /**
     * This method goes through each branch of a gameState recursively,
     * and gives back the highest heuristic value that his branches had.
     * It calls the min function, which calls the max function, and so on...
     * It stops if we get to our desired depth or if we have no possible turns.
     * The best turn for this branch will be saved in the attribute gameMove.
     * It's pretty much the standard alpha/beta pruning code on wikipedia.
     *
     * @param depth        The current depth, counting down.
     * @param currentState The board state of this node.
     * @param alpha        The current maximum heuristic value that the process found.
     * @param beta         The current minimum heuristic value that the process found.
     * @return The maximum heuristic value that this node found.
     */
    private int max(int depth, GameState currentState, int alpha, int beta) {
        if (depth == 0 || currentState.amountPossibleTurns() == 0) {
            //TODO: Insert a good heuristic for the currentState here.
            return currentState.getHeuristic();
        }
        int maxWert = alpha;

        //TODO: Maybe we need a better check for the field side.
        int nextIndex = setNextIndex(currentState);
        int lastIndex = setNextIndex(currentState) + 6;
        //go through all branches here.
        while (nextIndex < lastIndex) {
            if (currentState.getCells()[nextIndex].getBeans() != 0) {
                GameState expandedState = expandGameState(currentState, nextIndex);
                int wert = min(depth - 1, expandedState, maxWert, beta);
                if (wert > maxWert) {
                    maxWert = wert;
                    if (maxWert >= beta) {
                        break;
                    }
                    if (depth == TREE_DEPTH) {
                        gameMove = nextIndex;
                        System.out.println("Von Index " + nextIndex + " expandiert. => " + gameMove);
                    }
                }
            }
            nextIndex++;
        }
        return maxWert;
    }

    /**
     * Just like max a part of min/max or alpha/beta pruning.
     * Goes through all branches of his node and returns the lowest heursitic value that it found.
     *
     * @param depth        The current depth, counting down.
     * @param currentState The board state of this node.
     * @param alpha        The current maximum heuristic value that the process found.
     * @param beta         The current minimum heuristic value that the process found.
     * @return The minimum heuristic value that this node found.
     */
    private int min(int depth, GameState currentState, int alpha, int beta) {
        if (depth == 0 || currentState.amountPossibleTurns() == 0) {
            //TODO: Insert a good heuristic for the currentState here.
            return currentState.getHeuristic();
        }
        int minWert = beta;

        //TODO: Maybe we need a better check for the field side.
        int  nextIndex = setNextIndex(currentState);
        int  lastIndex = setNextIndex(currentState) + 6;
        while (nextIndex < lastIndex) {
            if (currentState.getCells()[nextIndex].getBeans() != 0) {
                GameState expandedState = expandGameState(currentState, nextIndex);
                int wert = max(depth - 1, expandedState, alpha, minWert);
                if (wert < minWert) {
                    minWert = wert;
                    if (minWert <= alpha) {
                        break;
                    }
                }
            }
            nextIndex++;
        }
        return minWert;
    }

    /**
     * @param currentState current gameState
     * @return Returns the first index of the current players field.
     */
    private int setNextIndex(GameState currentState) {
        int j;
        if (currentState.isCurrentPlayer()) {
            j = 0;
        } else {
            j = 6;
        }
        return j;
    }

    private GameState expandGameState(GameState currentState, int nextIndex){
        GameState expandedState = new GameState(currentState.getCells());
        expandedState.setCurrentPlayer(!currentState.isCurrentPlayer());
        expandedState.changeCurrentGamestate(nextIndex + 1);
        return expandedState;
    }

    private GameState getWorstPossibleTurn(GameState currentState, int firstIndex, int lastIndex){
        GameState worstTurn = null;
        for(int i = firstIndex; i < lastIndex; i++) {
            if(worstTurn == null) {
                worstTurn = expandGameState(currentState, i);
            } else {
                if(worstTurn.getHeuristic() > expandGameState(currentState, i).getHeuristic()){
                    worstTurn = expandGameState(currentState, i);
                }
            }
        }
        return worstTurn;
    }

    public int getGameMove() {
        return gameMove;
    }
    /*
I think that my implementation of alpha/beta pruning is somewhat right.
Maybe there are problems with some parts of it. maybe the heuristic must be improved.
I've started counting firstPlayer points in each GameState.
Before doing anything to gameState, please define the current firstPlayer with .setCurrentPlayer.

I made changes in BohnenspielAI, GameTree, GameState, and BeanCell.
BohnenspielAI:
I replaced the random turns with new GameTrees and their Index.
I added 2 final boolean values. The beginning firstPlayer is playerOne, the second firstPlayer is playerTwo.
Before calling .changeCurrentGamestate(), please set .setCurrentPlayer(boolean) with the above firstPlayer values.
GameTree:
Just look at the javadoc, I implemented the alpha/beta pruning.
GameState:
I changed distributeBeansOverCurrentGameState to count the firstPlayer points. I need the currentPlayer variable for this.
BeanCell:
gave collectBeans a return value of the collected beans to count them in GameState.
     */
}