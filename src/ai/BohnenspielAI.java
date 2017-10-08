package ai;

import java.util.Random;

public class BohnenspielAI {

    private static final int INIT_BEANS = 6;
    private static final int NUMBER_OF_CELLS = 12;
    private static final boolean AI_IS_PLAYER_ONE = true;
    private boolean startPlayer = false; //true if this AI is the start player of the game
    private BeanCell[] cells = new BeanCell[12];
    private GameState gameState;

    Random rand = new Random();

    public BohnenspielAI() {
        for (int i = 0; i < NUMBER_OF_CELLS; i++) {
            cells[i] = new BeanCell(i + 1, INIT_BEANS);
        }

        for (int i = 0; i < NUMBER_OF_CELLS; i++) {
            cells[i].setNextCell(cells[(i + 1) % 12]);
            cells[i].setPreviousCell(cells[(i + 11) % 12]);
        }
        gameState = new GameState(cells, 0, 0);

    }

    /**
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

    private int doMove(int enemyIndex, boolean aiIsPlayerOne) {
        int index;
        gameState.setCurrentPlayer(aiIsPlayerOne);
        gameState.changeCurrentGamestate(enemyIndex);
        //TODO: method to decide next AI-turn is called here2, we still have to improve.
        System.out.println("Player 1: " + gameState.getPlayerOnePoints());
        System.out.println("Player 2: " + gameState.getPlayerTwoPoints());
        if(gameState.getPlayerTwoPoints() == 26){
            System.out.println("ACHTUNG AB HIER!!!!!!!!");
        }
        index = choseIndex(aiIsPlayerOne);
        System.out.println("Index: " + index);
        printCells();
        return index;
    }

    private int choseIndex(boolean aiIsPlayerOne) {
        int index;
        gameState.setCurrentPlayer(!aiIsPlayerOne);
        GameTree gameTree = new GameTree(gameState, !aiIsPlayerOne);
        index = gameTree.getGameMove(gameState) + 1;
        gameState.changeCurrentGamestate(index);
        return index;
    }

    private void printCells(){
        for (int i = 0; i < 12; i++) {
            if (i == 11) {
                System.out.println(gameState.getCells()[i].getBeans());
            } else {
                System.out.print(gameState.getCells()[i].getBeans() + ", ");
            }
        }
        System.out.println("Heuristik: " + gameState.getHeuristic());
    }

    // TODO: This Method should be used to generate the index for my next turn
    private void generateTree() {
        new GameTree(gameState, false);
    }

    /**
     * TODO: AI Logic will be implemented here
     */
    private int magicMethodThatWinsTheGame() {

        return -1;
    }
}
