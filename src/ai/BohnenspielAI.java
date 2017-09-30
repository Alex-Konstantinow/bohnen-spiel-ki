package ai;

import java.util.Random;

public class BohnenspielAI {

    private final static int INIT_BEANS = 6;
    private final static int NUMBER_OF_CELLS = 12;

    private BeanCell[] cells = new BeanCell[12];

	Random rand = new Random();

	public BohnenspielAI() {
	    for(int i = 0; i < NUMBER_OF_CELLS; i++) {
	        cells[i] = new BeanCell(++i, INIT_BEANS);
        }
        for(int i = 0; i < NUMBER_OF_CELLS; i++) {
	        cells[i].setNextCell(cells[(i + 1) % 12]);
	        cells[i].setPreviousCell(cells[(i + 11) % 12]);
        }
    }
	
	/**
	* @param enemyIndex The index that refers to the field chosen by the enemy in the last action.
    *                   If this value is 0, than the AI is the starting player and has to specify the first move.
	* @return Return The index that refers to the field of the action chosen by this AI.
	*/
	public int getMove(int enemyIndex) {
		int index = 0;
		// have to choose the first move
		if (enemyIndex == -1) {
            //TODO: method to decide next AI-turn is called here
			index = rand.nextInt(6) + 1;
		}
		// enemy acted and i have to react
		else if (enemyIndex > 0 && enemyIndex <= 6) {
		    changeCurrentGamestate(enemyIndex);
		    //TODO: method to decide next AI-turn is called here
			index = rand.nextInt(6) + 7;
		}
		else if (enemyIndex > 6 && enemyIndex <= 12) {
		    changeCurrentGamestate(enemyIndex);
            //TODO: method to decide next AI-turn is called here
			index = rand.nextInt(6) + 1;
		}
		return index;
	}

    /**
     * Changing the game state after the enemys move.
     * @param enemyIndex is the index of the chosen cell the enemy emptied
     */
	private void changeCurrentGamestate(int enemyIndex) {
	    int chosenIndex = --enemyIndex;
	    int numberOfBeans = cells[chosenIndex].getBeans();
	    cells[chosenIndex].emptyCell();
	    cells[chosenIndex].distributeBeans(numberOfBeans);
    }

    /**
     * TODO: AI Logic will be implemented here
     */
    private int magicMethodThatWinsTheGame() {
	    return -1;
    }
	


}
