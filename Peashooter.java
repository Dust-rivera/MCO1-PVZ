/** Represents a Peashooter that inherits the Plant class
 * @author Deveza, Jerry King 
 * @author Rivera, Dustine Gian
 * @version 1.0
 */
public class Peashooter extends Plant{

    private int damage = 10; 
    private int dirDamage = 15;

    /**
     * This creates a Peashooter object given its x and y position
     * @param x the x position of the peashooter
     * @param y the y position of the peashooter
     */
    public Peashooter(int x, int y){
        super(100, 60, x, y, 6, 9);
    }

    /** This updates the Peashooter given the board
     * @param board the board to be updated
     */
    @Override
    public void update(Board board){
        this.increaseTick();

        if(Plant.peashooterCD != 0) 
            Plant.peashooterCD--;

        if(this.getTick() % this.getSPEED() == 0){
            Tile tile = board.getTile(this.getXPosition(), this.getYPosition());
            int row = board.getTileRow(tile);
            int col = board.getTileCol(tile);

            for(int i = col; i < this.getRange(); i++){
                Tile target = board.getTile(row, i);

                Zombie zombie = target.getZombie(0);

                if(zombie != null){
                    if(i == col){
                        zombie.takeDamage(dirDamage);
                    }else{
                        zombie.takeDamage(damage);
                    }
                        board.setMessage("Zombie damaged!"); 
                    if(zombie.isDead()){
                        target.removeZombie(0);
                        board.setMessage("Zombie dead!"); 
                        break;
                    }
                    break;
                }
            }
        }
    }
}
