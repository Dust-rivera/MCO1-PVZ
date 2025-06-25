public class Sunflower extends Plant{

    private int tick = 0;
    private final int BASE_TICK = 96; 

    public Sunflower(int x, int y){
        super(50, 6, 15, x, y);
    }

    @Override
    public void update(Board board){
        tick++;

        if(Plant.sunflowerCD != 0) 
            Plant.sunflowerCD--;
        if(tick % BASE_TICK == 0){
            board.generateSun();
            tick = 0;
        }
    }

    public void generateSun(Board board){
        board.generateSun();
    }

}
