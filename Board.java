import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {

    private Tile[][] board;
    private int tickCount = 0;
    private int secondsPassed = 0;  // Track the number of seconds passed
    private User player;
    private List<Zombie> zombieList;
    private int sunCount = 0;
    private boolean finalWaveFlag = true;


    public Board(User player) {
        this.player = player;
        this.zombieList = new ArrayList<>();

        // Initialize the game board
        board = new Tile[5][9];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 9; j++) {
                board[i][j] = new Tile();
            }
        }

       
    }

    public void moveZombies() {
    List<Zombie> zombiesToRemove = new ArrayList<>();

    for (Zombie zombie : zombieList) {
        int x = zombie.getXPosition();
        int y = zombie.getYPosition();

        if (zombie.isDead()) {
            board[y][x].setZombie(null); // remove zombie from board
            zombiesToRemove.add(zombie);
            System.out.println("Zombie at (" + y + ", " + x + ") died and was removed.");
            continue; // skip this zombie’s turn
        }
        // Attack logic
        if (x >= 0 && x < 9) {
            Tile tile = board[y][x];
            Plant plant = tile.getPlant();

            if (plant != null) {
                zombie.incrementAttackTick();
                if (zombie.getAttackTick() >= 4) { // attack every second
                    plant.decreaseHealth(zombie.getDamage());
                    zombie.resetAttackTick();
                    System.out.println("Zombie at (" + y + ", " + x + ") attacked plant: " + plant.getHealth() + " HP left");

                    if (plant.getHealth() <= 0) {
                        tile.setPlant(null);
                        System.out.println("Plant at (" + y + ", " + x + ") died.");
                    }
                }
                continue; // stop zombie movement when attacking
            }
        }

        // Remove from old tile
        if (zombie.getXPosition() >= 0 && zombie.getXPosition() < 9) {
            board[y][x].setZombie(null);
        }

         // Move if no plant blocks
        if (x == 0) {
            // zombie.incrementTicksAtCol0();
            // board[y][newX].setZombie(zombie);
            // if (zombie.getTicksAtCol0() >= 4) {
            //     zombiesToRemove.add(zombie);
            //     System.out.println("Zombie left the screen after 4 ticks at column 0.");
            // }
            zombiesToRemove.add(zombie);
            //System.exit(0);// GAME ENDS

        } else if (x > 0 && x < 9) {
            board[y][x - 1].setZombie(zombie);
        }
        zombie.move();
    }

        zombieList.removeAll(zombiesToRemove);
    }

    // Generate sun every 40 ticks (1 second)
    public void generateSun() {
        sunCount++;
    }

    // Spawn a zombie at a random row, at the rightmost column
    public void spawnZombie() {
        Random rand = new Random();
        int row;
        do {
           row = rand.nextInt(5);  
        }while(board[row][8].isOccupied());
         // Random row (0-4)
        Zombie zombie = new Zombie();
        zombie.setYPosition(row);
        placeZombie(row, 8, zombie);  // Spawn zombie at the last column (rightmost)
        //System.out.println("Zombie spawned at (" + row + ", 8) at time: " + secondsPassed);
        if (!(secondsPassed >= 171 && secondsPassed <= 180)) {
                Driver.message = "Zombie spawned at (" + row + ", 8) at time: " + secondsPassed;
        }
        
    }

    // public void spawnZombie(int x) {
    //     Zombie zombie = new Zombie();
    //     zombie.setYPosition(x);
    //     placeZombie(x, 8, zombie);  // Spawn zombie at the last column (rightmost)
    //     //System.out.println("Zombie spawned at (" + row + ", 8) at time: " + secondsPassed);
    //     Driver.message = "Zombie spawned at (" + x + ", 8) at time: " + secondsPassed;
    // }

    // Spawn wave of zombies from 171 to 180 seconds
    public void spawnWaveOfZombies() {
        Random rand = new Random();
        for (int i = 0; i < 5; i++) {  // Wave of 10 zombies
            // int row = rand.nextInt(5);  // Random row (0-4)
            // Zombie zombie = new Zombie();
            // placeZombie(row, 8, zombie);
            spawnZombie();  // Place zombies at the last column
            //System.out.println("Wave zombie spawned at (" + row + ", 8) at time: " + secondsPassed);
        }
    }

    // Place a zombie at the specified row and column
    public void placeZombie(int row, int col, Zombie zombie) {
        if (!board[row][col].isOccupied()) {
            board[row][col].setZombie(zombie);
            zombieList.add(zombie);
        }else{
            // System.out.println("didnt work");
            // System.exit(0);
             spawnZombie();
        }
    }

    // Place a plant on the board at the specified row and column
    public void placePlant(int row, int col, Plant plant) {
        if (!board[row][col].isOccupied()) {
            board[row][col].setPlant(plant);
            //System.out.println("Placed plant at (" + row + ", " + col + ")");
            Driver.message = "Placed plant at (" + row + ", " + col + ")";
        } else {
            //System.out.println("Tile (" + row + ", " + col + ") is already occupied.");
            Driver.message = "Tile (" + row + ", " + col + ") is already occupied.";
        }
    }

    // Update the board based on the timer and game logic
    public void update() {
        tickCount++;

        // Every 40 ticks (1 second), generate sun
        if (tickCount % 40 == 0) {
            generateSun();
        }

        // Spawn zombies based on the timer intervals
        if (tickCount % 4 == 0) {
            secondsPassed++;
            //moveZombies();

            // Drop sun every 10 seconds
            if (secondsPassed % 10 == 0) {
                generateSun();
            }

            // Zombie spawning rules based on specified time periods
            if (secondsPassed >= 30 && secondsPassed <= 80 && secondsPassed % 10 == 0) {
                spawnZombie();
            } else if (secondsPassed >= 81 && secondsPassed <= 140 && secondsPassed % 5 == 0) {
                spawnZombie();
            } else if (secondsPassed >= 141 && secondsPassed <= 170 && secondsPassed % 3 == 0) {
                spawnZombie();
            } else if (secondsPassed >= 171 && secondsPassed < 180 && !board[0][8].isOccupied()
                && !board[1][8].isOccupied()&& !board[2][8].isOccupied()&& !board[3][8].isOccupied() && !board[4][8].isOccupied() && finalWaveFlag) {
                finalWaveFlag = false;
                Driver.message = "A wave of zombies has appeared";
                spawnWaveOfZombies();
            } else if (secondsPassed == 180) {
                System.exit(0);
                System.out.print("GAME WON!!!!!");
            }
        }

        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                Plant plant = board[r][c].getPlant();
                if (plant != null) {
                    plant.update(this);
                }
            }
        }

        // Move zombies every 4 ticks (1 second)
        if (tickCount % 30 == 0) {
            moveZombies();
        }

        // Increment secondsPassed every 4 ticks (1 second)
        

        // Stop the game when the timer reaches 180 seconds
        // if (secondsPassed >= 180) {
        //     stopGameTimer();  // Stop the game timer when the game ends
        //     System.out.println("Game Over! The timer has reached zero.");
        // }
    }

    // Display the current state of the board
     public void display() {
        for (int i = 0; i < 5; i++) {
            System.out.println("---------------------------------------------");
            for (int j = 0; j < 9; j++) {
                if (j == 0) System.out.print("| ");

                Tile tile = board[i][j];
                String cell = "0";

                // for (Zombie zombie : zombieList) {
                //     if (zombie.getYPosition() == i && zombie.getXPosition() == j) {
                //         cell = "Z";
                //         break;
                //     }
                // }

                if(tile.getZombie() != null){
                    cell = "Z";
                }

                if (cell.equals("0") && tile.getPlant() != null) {
                    Plant plant = tile.getPlant();
                    if (plant instanceof Sunflower) {
                        cell = "S";
                    } else if (plant instanceof Peashooter) {
                        cell = "P";
                    }
                }

                System.out.print(cell + " | ");
            }
            System.out.println();
        }
        System.out.println("---------------------------------------------");
    }

    public void setSun(){
        sunCount = 0;
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

    public int getSunCount() {
        return sunCount;
    }
}