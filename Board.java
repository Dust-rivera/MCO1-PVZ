public class Board {
    
    private Plant[] plants;
    private Zombie[] zombies;
    private Tile[][] board;
    private int plantCount = 0;
    private int tickCount = 0;
    private int sunCount = 0;
    private User player;

    public Board(User player) {

        this.player = player;

        board = new Tile[5][9];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 9; j++) {
                board[i][j] = new Tile();
            }
        }

        //gameTimer = new GameTimer(this);
        //gameTimer.start(); 
      //yooooooooboard
    }

    public void setSun(){
        sunCount = 0;
    }

    public int getSunCount() {
        return sunCount;
    }

    public int getRows(){
        return board.length;
    }

    public int getCol(){
        return board[0].length;
    }

    public Tile getTile(int row, int col){
        return board[row][col];
    }

    public int getTileRow(Tile target){
        for(int r = 0; r < board.length; r++){
            for(int c = 0; c < board[0].length; c++){
                if(board[r][c] == target){
                    return r;
                }
            }
        }
        return -1;
    }

    public int getTileCol(Tile target){
        for(int r = 0; r < board.length; r++){
            for(int c = 0; c < board[0].length; c++){
                if(board[r][c] == target){
                    return c;
                }
            }
        }
        return -1;
    }

    public void generateSun(){
        sunCount++;
    }

    public void update(){
        tickCount++;
        
        if(tickCount % 40 == 0){
            generateSun();
        }
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                Plant plant = board[r][c].getPlant();
                if (plant != null) {
                    plant.update(this);
                }
            }
        }

    }

    public void display() {
    for (int i = 0; i < 5; i++) {
        System.out.println("---------------------------------------------");
        for (int j = 0; j < 9; j++) {
            if (j == 0) System.out.print("| "); // start of row
            System.out.print((board[i][j].isOccupied() ? "1" : "0") + " | ");
        }
        System.out.println();
    }
    System.out.println("---------------------------------------------");
    }

    public void placePlant(int row, int col, Plant plant){
        //plants[plantCount] = plant;
        if(!board[row][col].isOccupied()){
            board[row][col].setPlant(plant);
        }
        System.out.println("Placed plant at (" + row + ", " + col + ")");
    }

    public void placeZombie(int row, int col, Zombie zombie){
        if(!board[row][col].isOccupied()){
            board[row][col].setZombie(zombie);
        }
        System.out.println("Zombie is at (" + row + ", " + col + ")");
    }

    public static void main(String[] args){
        // Board b = new Board(new User());
        // System.out.println(b.getTileRow(b[3][3]));
    }

}