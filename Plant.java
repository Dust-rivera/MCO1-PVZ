public class Plant {
    private int xPosition;
    private int yPosition;
    private int cost;
    private int health;
    private static int plantCount = 0;
    private int dirDamage;
    private int cooldownTick = 0;

    public Plant(int cost, int health, int dirDamage, int x, int y){
        xPosition = x;
        yPosition = y;
        this.cost = cost;
        this.health = health;
        this.dirDamage = dirDamage;
    }

    public void update(Board board){
        
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

    public static void main(String[] args){
        Sunflower s = new Sunflower(1, 1);
        System.out.println(s.getXPosition() + s.getYPosition());
    }
}