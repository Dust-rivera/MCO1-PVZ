public class Zombie {

    private int speed;
    private int damage;
    private int health = 3;
    private static int zombieCount = 0;


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