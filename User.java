public class User {

    private int sunCount;

    public User(){
        sunCount = 50;
    }

    public void buyPlant(int amount){
        sunCount -= amount;
    }

    public void collectSun(int amount, Board board){
        board.setSun();
        sunCount += (amount * 25);

    }

    public int getSunCount() {
        return sunCount;
    }


    
}