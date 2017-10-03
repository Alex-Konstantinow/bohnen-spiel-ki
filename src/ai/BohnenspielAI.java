package ai;

import java.util.Random;

public class BohnenspielAI {

    private final static int INIT_BEANS = 6;
    private final static int NUMBER_OF_CELLS = 12;

    private boolean startPlayer = false; //true if this AI is the start player of the game

    private BeanCell[] cells = new BeanCell[12];
    private GameState gameState;

	Random rand = new Random();

	public BohnenspielAI() {
	    for(int i = 0; i < NUMBER_OF_CELLS; i++) {
	        cells[i] = new BeanCell(i+1, INIT_BEANS);
        }

        for(int i = 0; i < NUMBER_OF_CELLS; i++) {
            cells[i].setNextCell(cells[(i + 1) % 12]);
            cells[i].setPreviousCell(cells[(i + 11) % 12]);
        }
        gameState = new GameState(cells);

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
            //TODO: method to decide next AI-turn is called here
            startPlayer = !startPlayer;
            gameState.setStartPlayer(startPlayer);
            for(int i = 0; i<12; i++) {
                if (i == 11) {
                    System.out.println(gameState.getCells()[i].getBeans());
                }
                else {
                    System.out.print(gameState.getCells()[i].getBeans() + ", ");
                }
            }
			index = rand.nextInt(6) + 1;
			gameState.changeCurrentGamestate(index);
		}
		// enemy acted and i have to react
		else if (enemyIndex > 0 && enemyIndex <= 6) {
		    gameState.changeCurrentGamestate(enemyIndex);
		    //TODO: method to decide next AI-turn is called here
			index = rand.nextInt(6) + 7;
			gameState.changeCurrentGamestate(index);
            System.out.println("Index: " + index);
            for(int i = 0; i<12; i++) {
                if (i == 11) {
                    System.out.println(gameState.getCells()[i].getBeans());
                }
                else {
                    System.out.print(gameState.getCells()[i].getBeans() + ", ");
                }
            }
			System.out.println("Heuristik: " + gameState.getHeuristic());
		}
		else if (enemyIndex > 6 && enemyIndex <= 12) {
		    gameState.changeCurrentGamestate(enemyIndex);
            //TODO: method to decide next AI-turn is called here2
			index = rand.nextInt(6) + 1;
            gameState.changeCurrentGamestate(index);
            System.out.println("Index: " + index);
            for(int i = 0; i<12; i++) {
                if (i == 11) {
                    System.out.println(gameState.getCells()[i].getBeans());
                }
                else {
                    System.out.print(gameState.getCells()[i].getBeans() + ", ");
                }
            }
            System.out.println("Heuristik: " + gameState.getHeuristic());
		}
		return index;
	}

	// TODO: This Method should be used to generate the index for my next turn
	private void generateTree(){
	    new GameTree(gameState);
    }

    /**
     * TODO: AI Logic will be implemented here
     */
    private int magicMethodThatWinsTheGame() {

        return -1;
    }
}
