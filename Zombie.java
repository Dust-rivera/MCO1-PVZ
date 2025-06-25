public class Zombie {

    private int xPosition;
    private int yPosition;
    private int health = 8;
    private int ticksAtCol0 = 0;
    private int attackTick = 0;
    private long lastAttackTime = 0;

    private static final int SPEED = 30;
    private final int DAMAGE = 1;
    private final int attackCooldown = 2000;

    public Zombie(int yPosition) { 
        this.xPosition = 8;        
        this.yPosition = yPosition;
    }

    public int getAttackTick() {
        return attackTick;
    }
    
    public int getDamage() {
        return DAMAGE;
    }

    public int getXPosition() {
        return xPosition;
    }

    public int getYPosition() {
        return yPosition;
    }

    public int getHealth() {
        return health;
    }

    public int getTicksAtCol0() {
        return ticksAtCol0;
    }

    public long getLastAttackTime() {
        return lastAttackTime;
    }

    public static int getSpeed(){
        return SPEED;
    }

    public void setYPosition(int y) {
        this.yPosition = y;
    }

    public void setXPosition(int x) {
        this.xPosition = x;
    }

    public void setLastAttackTime(long time) {
        this.lastAttackTime = time;
    }

    public void takeDamage(int damage){
        health -= damage;
    }

    public void incrementAttackTick() {
        attackTick++;
    }

    public void resetAttackTick() {
        attackTick = 0;
    }

    public void move() {
        if (xPosition > 0) {
            xPosition -= SPEED;
        }
    }

    public void decreaseHealth(int amount) {
        health -= amount;
    }

    public boolean isDead() {
        return health <= 0;
    }

    public void incrementTicksAtCol0() {
        ticksAtCol0++;
    }

    public boolean canAttack() {
        return System.currentTimeMillis() - lastAttackTime >= attackCooldown;
    }
}