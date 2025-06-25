public class Zombie {
    private int xPosition;
    private int yPosition;
    private final int speed = 1;
    private final int damage = 10;
    private int health = 10;
    private int ticksAtCol0 = 0;
    private long lastAttackTime = 0;
    private final int attackCooldown = 2000; // 2 seconds
    private int attackTick = 0;

    public Zombie() { // accept row
        this.xPosition = 8; // rightmost column
        this.yPosition = yPosition;
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

    public int getAttackTick() {
        return attackTick;
    }

    public void move() {
        if (xPosition > 0) {
            xPosition -= speed;
        }
    }

    public int getXPosition() {
        return xPosition;
    }

    public int getYPosition() {
        return yPosition;
    }

    public void setYPosition(int y) {
        this.yPosition = y;
    }

    public void setXPosition(int x) {
        this.xPosition = x;
    }

    public int getDamage() {
        return damage;
    }

    public void decreaseHealth(int amount) {
        health -= amount;
    }

    public boolean isDead() {
        return health <= 0;
    }

    public int getHealth() {
        return health;
    }

    public void incrementTicksAtCol0() {
        ticksAtCol0++;
    }

    public int getTicksAtCol0() {
        return ticksAtCol0;
    }

    public long getLastAttackTime() {
        return lastAttackTime;
    }

    public void setLastAttackTime(long time) {
        this.lastAttackTime = time;
    }

    public boolean canAttack() {
        return System.currentTimeMillis() - lastAttackTime >= attackCooldown;
    }
}