public class Plant {
    private int xPosition;
    private int yPosition;
    private int cost;
    private int health;
    private int dirDamage;
    private final int BASE_TICK;

    public static final int SUNFLOWER_CD = 30;
    public static final int PEASHOOTER_CD = 30;

    public static int sunflowerCD = 0;
    public static int peashooterCD = 0;


    public Plant(int cost, int health, int dirDamage, int x, int y, int base){
        xPosition = x;
        yPosition = y;
        this.cost = cost;
        this.health = health;
        this.dirDamage = dirDamage;
        BASE_TICK = base;
    }

    public void update(Board board){
        
    }

    public static int getPeashooterCD() {
        return peashooterCD;
    }

    public static int getSunflowerCD() {
        return sunflowerCD;
    }

    public int getCost() {
        return cost;
    }

    public int getHealth() {
        return health;
    }
    
    public int getDirDamage() {
        return dirDamage;
    }

    public int getXPosition() {
        return xPosition;
    }

    public int getYPosition() {
        return yPosition;
    }

    public int getBASE(){
        return BASE_TICK;
    }

    public void decreaseHealth(int damage){
        this.health -= damage;
    }

    public boolean isDead(){
        return health <= 0;
    }
}