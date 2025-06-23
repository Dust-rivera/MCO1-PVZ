public class CherryBomb extends Plant {

    private static final int EXPLOSION_COOLDOWN = 50; // 50 is to be changed idk what value yet
    private final int baseTick = 2;
    private int tick = 0;
    private boolean isActivated = false;
   

    public CherryBomb(int x, int y) {
        super(150, 25, 90, x, y);  
    }

    @Override
    public void update(Board board) {
        if (isActivated) {
            tick++;
            if (tick >= EXPLOSION_COOLDOWN) {
                triggerExplosion(board);
                removeFromBoard(board);  // explosion
            }
        }
    }

    public void activate(Board board) {
        if (!isActivated) {
            isActivated = true;
            tick = 0;  // Reset cooldown
            System.out.println("CherryBomb activated!");
        }
    }

    private void triggerExplosion(Board board) {
        System.out.println("CherryBomb explosion triggered!");
      
        for (int row = Math.max(0, getYPosition() - 1); row <= Math.min(4, getYPosition() + 1); row++) {
            for (int col = Math.max(0, getXPosition() - 1); col <= Math.min(8, getXPosition() + 1); col++) {
                board.damageZombiesInRange(row, col, getDirDamage()); // 3x3 damage
            }
        }
    }

    private void removeFromBoard(Board board) {
        board.removePlant(this);  // Remove the CherryBomb from the board
    }

}
