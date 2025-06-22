public class Zombie {

    private int speed;
    private int damage;
    private int health;
    private static int zombieCount = 0;

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