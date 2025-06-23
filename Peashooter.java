public class Peashooter extends Plant{

    //private final int attackSpeed = 2;
    private final int baseTick = 2;
    private int damage = 2; 
    private int tick = 0;

    public Peashooter(int x, int y){
        super(4, 10, 15, x, y);
    }

    public void update(Board board){
        tick++;
    }

    // public int getAttackSpeed() {
    //     return attackSpeed;
    // }



}
