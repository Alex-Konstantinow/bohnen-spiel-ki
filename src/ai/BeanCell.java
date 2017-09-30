package ai;

public class BeanCell {

    /**
     * Represents one cell of beans. One game has 12 BeanCell´s, all 12 BeanCells forming together the game state
     */

    private int index;
    private int beans;
    private BeanCell nextCell;
    private BeanCell previousCell;

    public BeanCell(int index, int beans){
        this.index = index;
        this.beans = beans;
    }

    /**
     * Distributes all beans inside the selected cell to the following cells.
     * @param beansLeft number of beans to distribute
     */
    public void distributeBeans(int beansLeft) {
        int beansToDistribute = --beansLeft;
        if(beansLeft > 1) {
            nextCell.increaseBeans();
            nextCell.distributeBeans(beansToDistribute);
        } else {
            // last cell after the beans are distributed.
            this.increaseBeans();
            this.collectBeans();
        }
    }

    private void increaseBeans(){
        this.beans++;
    }

    /**
     * Checks if the last field after distribution of beans contains 6, 4 or 2 beans. If yes, it checks the previous
     * cell until a cell does not contain 6, 4 or 2 beans.
     * TODO: possible to implement memory of the current points collected by each player
     */
    private void collectBeans() {
        int currentBeans = this.getBeans();
        if(currentBeans == 6 || currentBeans == 4 || currentBeans == 2) {
            this.emptyCell();
            previousCell.collectBeans();
        }
    }

    public void emptyCell(){
        this.beans = 0;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getBeans() {
        return beans;
    }

    public BeanCell getNextCell() {
        return nextCell;
    }

    public void setNextCell(BeanCell nextCell) {
        this.nextCell = nextCell;
    }

    public BeanCell getPreviousCell() {
        return previousCell;
    }

    public void setPreviousCell(BeanCell previousCell) {
        this.previousCell = previousCell;
    }
}
