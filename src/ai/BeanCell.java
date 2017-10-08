package ai;

public class BeanCell {

    /**
     * Represents one cell of beans. One game has 12 BeanCell´s, all 12 BeanCells forming together the game state
     */

    private int index;
    private int beans;

    public BeanCell(int index, int beans) {
        this.index = index;
        this.beans = beans;
    }

    public void increaseBeans() {
        this.beans++;
    }

    /**
     * Checks if the last field after distribution of beans contains 6, 4 or 2 beans. If yes, it checks the previous
     * cell until a cell does not contain 6, 4 or 2 beans.
     */
    public int collectBeans() {
        int currentBeans = this.getBeans();
        if (currentBeans == 6 || currentBeans == 4 || currentBeans == 2) {
            int buffer = this.beans;
            this.emptyCell();
            return buffer;
        } else {
            return 0;
        }
    }

    public void emptyCell() {
        this.beans = 0;
    }

    public int getIndex() {
        return index;
    }

    public int getBeans() {
        return beans;
    }

}
