public class User {

    private int sunCount;

    public User(){
        sunCount = 50;
    }

    public void collectSun(int amount){
        sunCount += amount;
    }

    public int getSunCount() {
        return sunCount;
    }

    
    
}