public class Plant {
    private int xPosition;
    private int yPosition;
    private int cost;
    private int health;
    private static int plantCount = 0;
    private int dirDamage;

    public static final int SUNFLOWER_CD = 30;
    public static final int PEASHOOTER_CD = 30;
    public static final int CHERRYBOMB_CD = 200;

    public static int sunflowerCD = 0;
    public static int peashooterCD = 0;
    public static int cherryBombCD = 0;


    public Plant(int cost, int health, int dirDamage, int x, int y){
        xPosition = x;
        yPosition = y;
        this.cost = cost;
        this.health = health;
        this.dirDamage = dirDamage;
    }

    public void update(Board board){
        
    }

    public static int getCherryBombCD() {
        return cherryBombCD;
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

    public static int getPlantCount() {
        return plantCount;
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

    public void decreaseHealth(int damage){
        this.health -= damage;
    }

    public static void main(String[] args){
        Sunflower s = new Sunflower(1, 1);
        System.out.println(s.getXPosition() + s.getYPosition());
    }
}