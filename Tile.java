/** Represents a tile on the board
 * @author Deveza, Jerry King 
 * @author Rivera, Dustine Gian
 * @version 1.0
 */
public class Tile {

    private Plant plant = null;
    private Zombie zombie = null;

    /**
     * Creates a tile object
     */
    public Tile(){}

    /**
     * This gets the plant occupying the tile
     * @return The plant occupying the tile
     */
    public Plant getPlant() {
        return plant;
    }

    /**
     * This gets the zombie occupying the tile
     * @return The zombie occupying the tile
     */
    public Zombie getZombie() {
        return zombie;
    }

    /**
     * This modifies the plant occupying the tile
     * @param plant the plant to be assigned
     */
    public void setPlant(Plant plant) {
        this.plant = plant;
    }

    /**
     * This modifies the zombie occupying the tile
     * @param zombie The zombie to be assigned
     */
    public void setZombie(Zombie zombie) {
        this.zombie = zombie;
    }

    /**
     * This checks if tile is occupied by both plant and zombie
     * @return boolean if occupied
     */
    public boolean isOccupied(){
        return (plant != null || zombie != null);
    }

    /**
     * Removes the plant on the tile
     */
    public void removePlant(){
        this.plant = null;
    }

    /**
     * Removes the zombie on the tile
     */
    public void removeZombie(){
        this.zombie = null;
    }
}
