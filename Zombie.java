public class Zombie {

    private int speed;
    private int damage;
    private int health = 3;
    private static int zombieCount = 0;
    private int xPosition;  // Horizontal position on the board (starts from 8 to 0)
    private int yPosition;  // Vertical position (row) on the board

    // Default constructor sets the starting position
    public Zombie() {
        this.xPosition = 8;  // Start at the rightmost position (column 8)
        this.yPosition = (int) (Math.random() * 5);  // Random row for simplicity (0 to 4)
    }

    // Move the zombie to the left by 1 tile
    public void move() {
        if (this.xPosition > 0) {  // Ensure the zombie doesn't move past the leftmost side
            this.xPosition--;  // Move one step to the left (decrease xPosition)
        }
    }

    // Get the zombie's current xPosition (horizontal position)
    public int getXPosition() {
        return xPosition;
    }

    // Get the zombie's current yPosition (row position)
    public int getYPosition() {
        return yPosition;
    }

    public void takeDamage(int amount){
        health -= amount;
    }

    public boolean isDead(){
        return health <= 0;
    }

    public int getDamage() {
        return damage;
    }

    public int getHealth() {
        return health;
    }

    public int getSpeed() {
        return speed;
    }

    public static int getZombieCount() {
        return zombieCount;
    }
}