public class Board {
    
    private Plant[] plants;
    private Zombie[] zombies;
    private Tile[][] board;

    public Board(){
        board = new Tile[5][9];

        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 9; j++){
                board[i][j] = new Tile();
            }
        }
    }

    public void display(){
        
    }

    public void placePlant(int row, int col, Plant plant){
        if(!board[row][col].isOccupied()){
            board[row][col].setPlant(plant);
        }
        System.out.println("placed");
    }

}
