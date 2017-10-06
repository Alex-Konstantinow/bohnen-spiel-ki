package util;

import ai.GameState;

import java.util.Comparator;

public class MyTurnComparator implements Comparator<GameState> {
    @Override
    /**
     * Sortingpriority: Depth over heuristic, the deepest current expanded game state will be expanded next
     */
    public int compare(GameState left, GameState right) {
//        if(left.getDepthInTree() < right.getDepthInTree()) {
//            return -1;
//        }
//        if(left.getDepthInTree() > right.getDepthInTree()) {
//            return 1;
//        }
//        if(left.getDepthInTree() == right.getDepthInTree()) {
//            if(left.getHeuristic() < right.getHeuristic()) {
//                return -1;
//            }
//            if(left.getHeuristic() > right.getHeuristic()) {
//                return 1;
//            }
//        }
        return 0;
    }
}
