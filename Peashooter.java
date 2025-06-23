public class Peashooter extends Plant{

    //private final int attackSpeed = 2;
    private final int baseTick = 2;
    private int damage = 2; 
    private int tick = 0;
    private int cooldownTick = 0;

    public Peashooter(int x, int y){
        super(100, 6, 15, x, y);
    }

    public void update(Board board){
        tick++;

        if(tick % 5 == 0){
            Tile tile = board.getTile(this.getXPosition(), this.getYPosition());
            int row = board.getTileRow(tile);
            int col = board.getTileCol(tile);

            for(int i = col + 1; i < board.getCol(); i++){
                Tile target = board.getTile(row, i);
                Zombie zombie = target.getZombie();

                if(zombie != null){
                    zombie.takeDamage(1);
                        System.out.println("zombie damage");
                    if(zombie.isDead()){
                        target.removeZombie();
                        System.out.println("zombie dead");
                        break;
                    }
                }
            }
        }
    }

    // public int getAttackSpeed() {
    //     return attackSpeed;
    // }



}
