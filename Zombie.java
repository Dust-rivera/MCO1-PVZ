/** Represents a Zombie
 * @author Deveza, Jerry King 
 * @author Rivera, Dustine Gian
 * @version 1.0
 */
public class Zombie {

    private int xPosition;
    private int yPosition;
    private int health = 8;
    private int attackTick = 0;

    private static final int SPEED = 30;
    private final int DAMAGE = 1;

    /**
     * Creates Zombie object given its y position and puts its x position as 8
     * @param yPosition the y position of the zombie
     */
    public Zombie(int yPosition) { 
        this.xPosition = 8;        
        this.yPosition = yPosition;
    }

    /**
     * This gets the attack tick of the zombie
     * @return an integer containing the attack tick
     */
    public int getAttackTick() {
        return attackTick;
    }
    
    /**
     * This gets the damage the zombie deals
     * @return an integer containing the damage the zombie deals
     */
    public int getDamage() {
        return DAMAGE;
    }

    /**
     * This gets the zombie's x position
     * @return an integer containing the zombie's x positon
     */
    public int getXPosition() {
        return xPosition;
    }

    /**
     * This gets the zombie's y position
     * @return an integer containing the zombie's y positon
     */
    public int getYPosition() {
        return yPosition;
    }

    /**
     * This gets the zombie's health
     * @return an integer containing the zombie's health
     */
    public int getHealth() {
        return health;
    }

    /**
     * This gets the zombie's speed based on how many ticks
     * @return an integer containing the zombie's spead based on how many ticks
     */
    public static int getSpeed(){
        return SPEED;
    }

    /**
     * This modifies the zombie's y position
     * @param y the new y position of zombie
     */
    public void setYPosition(int y) {
        this.yPosition = y;
    }

    /**
     * This modifies the zombie's x position
     * @param x the new x position of zombie
     */
    public void setXPosition(int x) {
        this.xPosition = x;
    }

    /**
     * This decreases the zombie's health based on the damage taken
     * @param damage the amount of damage going to be inflicted
     */
    public void takeDamage(int damage){
        health -= damage;
    }

    /**
     * This increases attack tick
     */
    public void incrementAttackTick() {
        attackTick++;
    }

    /**
     * This resets attack tick
     */
    public void resetAttackTick() {
        attackTick = 0;
    }

    /**
     * This moves the zombie's x position one unit to the left
     */
    public void move() {
        if (xPosition > 0) {
            xPosition -= 1;
        }
    }

    /**
     * This checks if the zombie's health is equal or below 0
     * @return a boolean if the zombie's health is equal or below 0
     */
    public boolean isDead() {
        return health <= 0;
    }
}