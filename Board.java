/**
 * Represents the main board of the game
 * @author Deveza, Jerry King 
 * @author Rivera, Dustine Gian
 * @version 1.0
 */
import java.util.ArrayList;
import java.util.Random;

public class Board {

    private Tile[][] board;
    private int tickCount = 0;
    private int secondsPassed = 0;
    private User player;
    private ArrayList<Zombie> zombieList;
    private int sunCount = 0;
    private boolean finalWaveFlag = true;
    private String message = "";
    private boolean running = true;
    private int numRows;
    private int numCols;

    /**
     * This creates a board object given the player
     * @param player the user of the board
     */
    public Board(User player, int row, int col) {
        this.player = player;
        this.zombieList = new ArrayList<>();
        numRows = row;
        numCols = col;

        board = new Tile[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                board[i][j] = new Tile();
            }
        }
    }

    /**
     * This gets the board's sun count
     * @return an integer containing the board's sun count
     */
    public int getSunCount() {
        return sunCount;
    }

    /**
     * This gets the board's zombie array list
     * @return an array list containing all the zombies on the board
     */
    public ArrayList<Zombie> getZombieList() {
        return zombieList;
    }

    /**
     * This gets the state of the finaWaveFlag boolean
     * @return a boolean containing the state of the finalWaveFlag variable
     */
    public boolean getFinalWaveFlag() {
        return finalWaveFlag;
    }

    /**
     * This gets the board's message
     * @return a String containing the board's message
     */
    public String getMessage(){
        return message;
    }
    
    /**
     * This gets the state of the running boolean
     * @return a boolean containing the state of the running variable
     */
    public boolean getRunning(){
        return running;
    }

    /**
     * This gets the number of rows of the board
     * @return and integer containing amount of rows
     */
    public int getRows() {
        return numRows;
    }

    /**
     * This gets the number of columns of the board
     * @return and integer containing amount of columns
     */
    public int getCol() {
        return numCols;
    }

    /**
     * This returns the specified tile given the row and column
     * @param row the row of the tile
     * @param col the column of the tile
     * @return a tile on the board
     */
    public Tile getTile(int row, int col) {
        return board[row][col];
    }

    /**
     * This gets the which row the tile given is on
     * @param target the tile to be row identified
     * @return an integer containing the row of targeted tile
     */
    public int getTileRow(Tile target) {
        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < numCols; c++) {
                if (board[r][c] == target) {
                    return r;
                }
            }
        }
        return -1;
    }

    /**
     * This gets the which column the tile given is on
     * @param target the tile to be column identified
     * @return an integer containing the column of targeted tile
     */
    public int getTileCol(Tile target) {
        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < numCols; c++) {
                if (board[r][c] == target) {
                    return c;
                }
            }
        }
        return -1;
    }

    /**
     * This modifies the board's message
     * @param string the string to modify the board's message
     */
    public void setMessage(String string){
        message = string;
    }

    /**
     * This modifies the state of the running variable
     * @param bool the boolean to modify the sate of the running variable
     */
    public void setRunning(boolean bool){
        running = bool;
    }

    /**
     * This sets the sun count back to zero
     */
    public void setSun() {
        sunCount = 0;
    }

    /**
     * This updates the board based on the player's input
     * @param board the board to be updated
     */
    public void input(Board board){
        if (player.getInput().equalsIgnoreCase("exit")) {
            this.setRunning(false);
        } else if (player.getInput().startsWith("S") || player.getInput().startsWith("s")) {
            String[] coordinate = player.getInput().split(" ");
            int row, col;
            try{
                row = Integer.parseInt(coordinate[1]) - 1;
                col = Integer.parseInt(coordinate[2]) - 1;
            }catch(NumberFormatException e){
                row = -1;
                col = -1; 
                this.setMessage("Invalid input");
            }
            if (player.getSunCount() >= 50 && Plant.sunflowerCD == 0) {
                if(row >= 0 && row < numRows && col >= 0 && col < numCols){
                    Plant.sunflowerCD = Plant.SUNFLOWER_REGEN;
                    board.placePlant(row, col, new Sunflower(row, col));
                }else{
                    this.setMessage("Input Out of Bounds");
                }
            } else if (player.getSunCount() >= 50 && Plant.sunflowerCD != 0) {
                this.setMessage("Sunflower on cooldown!");
            } else {
                this.setMessage("Not enough Sun!");

            }
            coordinate = null;
        } else if (player.getInput().startsWith("P") || player.getInput().startsWith("p")) {
            String[] coordinate = player.getInput().split(" ");
            int row, col;
            try{
                row = Integer.parseInt(coordinate[1]) - 1;
                col = Integer.parseInt(coordinate[2]) - 1;
            }catch(NumberFormatException e){
                row = -1;
                col = -1; 
                this.setMessage("Invalid input");
            }
            if (player.getSunCount() >= 100 && Plant.peashooterCD == 0) {
                if(row >= 0 && row < numRows && col >= 0 && col < numCols){
                    Plant.peashooterCD = Plant.PEASHOOTER_REGEN;
                    board.placePlant(row, col, new Peashooter(row, col));
                }else{
                    this.setMessage("Input Out of Bounds");
                }
            } else if (player.getSunCount() >= 100 && Plant.peashooterCD != 0) {
                this.setMessage("Peashooter on cooldown!");
            } else {
                this.setMessage("Not enough Sun!");
            }
        } else if (player.getInput().equalsIgnoreCase("c")) {
            if (board.getSunCount() == 0) {
                this.setMessage("No sun");
            } else {
                this.setMessage("Sun collected");
                player.collectSun(board.getSunCount(), board);
            }
        } else{
                this.setMessage("Invalid command");

        }
    }

    /**
     * This updates all the zombies on the board
     * in a certain amount of ticks
     */
    public void moveZombies() {
        ArrayList<Zombie> zombiesToRemove = new ArrayList<>();

        for (Zombie zombie : zombieList) {
            int col = zombie.getXPosition();
            int row = zombie.getYPosition();
            Tile tile = board[row][col];

            if (tile.getPlant() == null /*&& board[row][col - 1].getZombies().size() == 0*/) {
                zombie.move();
                board[row][col].removeZombie(zombie);
                board[row][col - 1].addZombie(zombie);
                
            }

            else if (zombie.isDead()) {
                board[row][col].removeZombie(zombie);
                zombiesToRemove.add(zombie);
                this.setMessage("Zombie at (" + (row + 1) + ", " + (col + 1) + ") died and was removed.");
            }
            else if (col >= 0 && col < numCols) {
                Plant plant = tile.getPlant();

                if (plant != null) {
                    if (col == 0) {
                        zombiesToRemove.add(zombie);
                        System.out.println("\nZombie has entered your home! \nGAME OVER");
                        this.setRunning(false);
                        break;
                    }
                    zombie.incrementAttackTick();
                    plant.decreaseHealth(zombie.getDamage());
                    zombie.resetAttackTick();
                    this.setMessage(
                            "Zombie at (" + (row + 1) + ", " + (col + 1) + ") attacked plant: " + plant.getHealth() + " HP left");

                    if (plant.isDead()) {
                        tile.setPlant(null);
                        plant = null;
                        System.out.println("Plant at (" + (row + 1) + ", " + (col + 1) + ") died.");
                    }
                    continue;
                }
            }

        }

        zombieList.removeAll(zombiesToRemove);
    }

    /**
     * This increases the sun count by one
     */
    public void generateSun() {
        sunCount++;
    }

    /**
     * This spawn a zombie on the board at the last column and on a random row
     */
    public void spawnZombie() {
        Random rand = new Random();
        int row = rand.nextInt(numRows);
        Zombie zombie = new Zombie(row);
        zombie.setYPosition(row);
        placeZombie(row, numCols - 1, zombie); 
        if (!(secondsPassed >= 171 && secondsPassed <= 180)) {
            this.setMessage("Zombie spawned at (" + (row + 1) + ", 8) at time: " + secondsPassed);
        }
    }

    public void spawnZombie(int x) {
        Zombie zombie = new Zombie(x);
        zombie.setYPosition(x);
        placeZombie(x, 8, zombie); 
        if (!(secondsPassed >= 171 && secondsPassed <= 180)) {
            this.setMessage("Zombie spawned at (" + (x + 1) + ", 8) at time: " + secondsPassed);
        }
    }

    /**
     * This spawns a wave of zombies on the board
     */
    public void spawnWaveOfZombies() {
        for (int i = 0; i < numRows; i++) {
            spawnZombie(); 
            //spawnZombie();
        }
    }

    /**
     * This places the zombie on the board given the zombie object, row, and column
     * @param row the row in which the zombie will be placed
     * @param col the column in which the zombie will be placed
     * @param zombie the zombie object to be placed on the board
     */
    public void placeZombie(int row, int col, Zombie zombie) {
        board[row][col].addZombie(zombie);
        zombieList.add(zombie);
    }

    /**
     * This places the plant on the board given the plant object, row, and column
     * @param row the row in which the plant will be placed
     * @param col the column in which the plant will be placed
     * @param plant the plant object to be placed on the board
     */
    public void placePlant(int row, int col, Plant plant) {
        if (!board[row][col].isPlantOccupied()) {
            board[row][col].setPlant(plant);
            player.buyPlant(plant.getCost());
            this.setMessage("Placed plant at (" + (row + 1) + ", " + (col + 1) + ")");
        } else {
            this.setMessage("Tile (" + (row + 1) + ", " + (col + 1) + ") is already occupied.");
        }
    }

    /**
     * This updates the board's logic every tick
     */
    public void update() {
        tickCount++;

        if (tickCount % 40 == 0) {
            generateSun();
        }

        if (tickCount % 4 == 0) {
            secondsPassed++;

            if (secondsPassed >= 30 && secondsPassed <= 80 && secondsPassed % 10 == 0) {
                spawnZombie();
            } else if (secondsPassed >= 81 && secondsPassed <= 140 && secondsPassed % 5 == 0) {
                spawnZombie();
            } else if (secondsPassed >= 141 && secondsPassed <= 170 && secondsPassed % 3 == 0) {
                spawnZombie();
            } else if (secondsPassed >= 171 && secondsPassed < 180 && /*!board[0][8].isOccupied()
                    && !board[1][8].isOccupied() && !board[2][8].isOccupied() && !board[3][8].isOccupied()
                    && !board[4][8].isOccupied() &&*/ finalWaveFlag) {
                finalWaveFlag = false;
                this.setMessage("A wave of zombies has appeared");
                spawnWaveOfZombies();
            }
        }

        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < numCols; c++) {
                Plant plant = board[r][c].getPlant();
                if (plant != null) {
                    board[r][c].getPlant().update(this);
                }
            }
        }

        if (tickCount % Zombie.getSpeed() == 0) {
            moveZombies();
        }

    }

    /**
     * This displays what the board look like
     * with the plants and zombies on the board
     */
    public void display() {
        for (int i = 0; i < numRows; i++) {
            System.out.println("---------------------------------------------");
            for (int j = 0; j < numCols; j++) {
                if (j == 0)
                    System.out.print("| ");

                Tile tile = board[i][j];
                String cell = "00";

                if (tile.getZombies().size() != 0) {
                    cell = "Z";
                    cell = cell.concat(Integer.toString(tile.getZombies().size()));
                }

                if (cell.equals("00") && tile.getPlant() != null) {
                    Plant plant = tile.getPlant();
                    if (plant instanceof Sunflower) {
                        cell = "S ";
                    } else if (plant instanceof Peashooter) {
                        cell = "P ";
                    }
                }

                System.out.print(cell + " | ");
            }
            System.out.println();
        }
        System.out.println("---------------------------------------------");
        System.out.println("Game message: " + this.getMessage());
        System.out.println("Sun dropped: " + this.getSunCount());
        System.out.println("Sun Points: " + player.getSunCount());
        System.out.print("Enter command: ");
    }    
}