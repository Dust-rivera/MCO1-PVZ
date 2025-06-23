public class Sunflower extends Plant{

    private  int tick = 0;

    public Sunflower(int x, int y){
        super(4, 10, 15, x, y);
    }

    @Override
    public void update(Board board){
        tick++;
        if(tick % 96 == 0){
            board.generateSun();
            tick = 0;
        }
    }

    public void generateSun(Board board){
        board.generateSun();
    }

}
