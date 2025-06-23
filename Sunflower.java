public class Sunflower extends Plant{

    private final int baseTick = 3;
    private  int tick = 0;

    public Sunflower(){
        super(4, 10, 15);
    }

    @Override
    public void update(Board board){
        tick++;
        if(tick == baseTick){
            board.generateSun();
            tick = 0;
        }
    }

    public void generateSun(Board board){
        board.generateSun();
    }

}
