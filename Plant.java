public class Plant {
    
    private int cost;
    private int health;
    private static int plantCount = 0;
    private int dirDamage;
    private int[][] position;

    public Plant(int cost, int health, int dirDamage){
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
}
