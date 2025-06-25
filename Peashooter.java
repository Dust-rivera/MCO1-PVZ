public class Peashooter extends Plant{

    private int damage = 1; 
    private int tick = 0;

    public Peashooter(int x, int y){
        super(100, 6, 15, x, y, 6);
    }

    public void update(Board board){
        tick++;

        if(Plant.peashooterCD != 0) 
            Plant.peashooterCD--;

        if(tick % this.getBASE() == 0){
            Tile tile = board.getTile(this.getXPosition(), this.getYPosition());
            int row = board.getTileRow(tile);
            int col = board.getTileCol(tile);

            for(int i = col; i < board.getCol(); i++){
                Tile target = board.getTile(row, i);
                Zombie zombie = target.getZombie();

                if(zombie != null){
                    zombie.takeDamage(damage);
                        //System.out.println("zombie damage");
                        board.setMessage("Zombie damaged!"); 
                    if(zombie.isDead()){
                        target.removeZombie();
                        // System.out.println("zombie dead");
                        board.setMessage("Zombie dead!"); 
                        break;
                    }
                    break;
                }
            }
        }
    }
}
