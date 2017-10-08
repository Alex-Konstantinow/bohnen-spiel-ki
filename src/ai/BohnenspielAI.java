package ai;

public class BohnenspielAI {

    private static final int INIT_BEANS = 6;
    private static final int NUMBER_OF_CELLS = 12;
    private static final boolean AI_IS_PLAYER_ONE = true;
    private boolean startPlayer = false;
    private BeanCell[] cells = new BeanCell[12];
    private GameState gameState;

    public BohnenspielAI() {
        for (int i = 0; i < NUMBER_OF_CELLS; i++) {
            cells[i] = new BeanCell(i + 1, INIT_BEANS);
        }
        gameState = new GameState(cells, 0, 0);
    }

    /**
     * Getting the indext of the next turn.
     *
     * @param enemyIndex The index that refers to the field chosen by the enemy in the last action.
     *                   If this value is -1, than the AI is the starting player and has to specify the first move.
     * @return Return The index that refers to the field of the action chosen by this AI.
     */
    public int getMove(int enemyIndex) {
        int index = 0;
        // have to choose the first move
        if (enemyIndex == -1) {
            startPlayer = !startPlayer;
            gameState.setStartPlayer(startPlayer);
            printCells();
            index = choseIndex(!AI_IS_PLAYER_ONE);
        }
        // enemy acted and i have to react
        else if (enemyIndex > 0 && enemyIndex <= 6) {
            index = doMove(enemyIndex, AI_IS_PLAYER_ONE);
        } else if (enemyIndex > 6 && enemyIndex <= 12) {
            index = doMove(enemyIndex, !AI_IS_PLAYER_ONE);
        }
        return index;
    }

    /**
     * Changing the game state after the enemy turn and gets the index for the ai´s next turn.
     *
     * @param enemyIndex
     * @param aiIsPlayerOne
     * @return
     */
    private int doMove(int enemyIndex, boolean aiIsPlayerOne) {
        int index;
        gameState.setCurrentPlayer(aiIsPlayerOne);
        gameState.changeCurrentGamestate(enemyIndex);
        index = choseIndex(aiIsPlayerOne);
        printCells();
        return index;
    }

    /**
     * Building the Game tree to get the best possible turn based on the heuristic.
     *
     * @param aiIsPlayerOne
     * @return the indext for ai`s next turn
     */
    private int choseIndex(boolean aiIsPlayerOne) {
        int index;
        gameState.setCurrentPlayer(!aiIsPlayerOne);
        GameTree gameTree = new GameTree(gameState, !aiIsPlayerOne);
        index = gameTree.getGameMove(gameState) + 1;
        gameState.changeCurrentGamestate(index);
        return index;
    }

    private void printCells() {
        for (int i = 0; i < 12; i++) {
            if (i == 11) {
                System.out.println(gameState.getCells()[i].getBeans());
            } else {
                System.out.print(gameState.getCells()[i].getBeans() + ", ");
            }
        }
    }
}
