public class Peashooter extends Plant{

    //private final int attackSpeed = 2;
    private final int BASE_TICK = 6;
    private int damage = 1; 
    private int tick = 0;

    public Peashooter(int x, int y){
        super(100, 6, 15, x, y);
    }

    public void update(Board board){
        tick++;

        if(tick % BASE_TICK == 0){
            Tile tile = board.getTile(this.getXPosition(), this.getYPosition());
            int row = board.getTileRow(tile);
            int col = board.getTileCol(tile);

            for(int i = col + 1; i < board.getCol(); i++){
                Tile target = board.getTile(row, i);
                Zombie zombie = target.getZombie();

                if(zombie != null){
                    zombie.takeDamage(damage);
                        //System.out.println("zombie damage");
                        Driver.message = "Zombie damaged!"; 
                    if(zombie.isDead()){
                        target.removeZombie();
                        // System.out.println("zombie dead");
                        Driver.message = "Zombie dead!"; 
                        break;
                    }
                    break;
                }
            }
        }
    }

    // public int getAttackSpeed() {
    //     return attackSpeed;
    // }



}
