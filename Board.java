import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Board {

    private Plant[] plants;
    private Zombie[] zombies;
    private Tile[][] board;
    private int tickCount = 0;
    private User player;
    private List<Zombie> zombieList;
    private Timer gameTimer;  // Timer for game updates

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

        // Initialize and start the game timer
        startGameTimer();
    }

    // Start the game timer to update every second
    private void startGameTimer() {
        gameTimer = new Timer();

        // Schedule the game loop to run every second (1000 milliseconds)
        gameTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                update();  // Call the update method to handle game logic
            }
        }, 0, 1000);  // Start immediately, then run every 1000ms (1 second)
    }

    // Cancel the game timer when the game ends
    public void stopGameTimer() {
        if (gameTimer != null) {
            gameTimer.cancel();
            System.out.println("Game Over! Timer stopped.");
        }
    }

    // Spawn a zombie based on intervals
    public void spawnZombie() {
        int row = (int) (Math.random() * 5);  // Random row for simplicity
        Zombie zombie = new Zombie();
        placeZombie(row, 8, zombie);  // Spawn zombie at the last column
    }

    // Handle the wave of zombies from 171 to 180 seconds
    public void spawnWaveOfZombies() {
        for (int i = 0; i < 10; i++) {  // Wave of 10 zombies
            int row = (int) (Math.random() * 5);  // Random row for each zombie
            Zombie zombie = new Zombie();
            placeZombie(row, 8, zombie);  // Spawn zombies at the last column
        }
        System.out.println("Wave of zombies spawned at time: " + tickCount);
    }

    // Place a plant on the board at the specified row and column
    public void placePlant(int row, int col, Plant plant) {
        if (!board[row][col].isOccupied()) {
            board[row][col].setPlant(plant);
            System.out.println("Placed plant at (" + row + ", " + col + ")");
        } else {
            System.out.println("Tile (" + row + ", " + col + ") is already occupied.");
        }
    }

    // Place a zombie on the board at the specified row and column
    public void placeZombie(int row, int col, Zombie zombie) {
        if (!board[row][col].isOccupied()) {
            board[row][col].setZombie(zombie);
            zombieList.add(zombie);
            System.out.println("Zombie is at (" + row + ", " + col + ")");
        }
    }

   public void update() {
    tickCount++;  // Increment tick count at the start of each tick

    // Spawn zombies based on defined intervals
    if (tickCount >= 30 && tickCount <= 80 && tickCount % 10 == 0) {
        spawnZombie();
    } else if (tickCount >= 81 && tickCount <= 140 && tickCount % 5 == 0) {
        spawnZombie();
    } else if (tickCount >= 141 && tickCount <= 170 && tickCount % 3 == 0) {
        spawnZombie();
    } else if (tickCount >= 171 && tickCount <= 180 && zombieList.isEmpty()) {
        spawnWaveOfZombies();
    }

    // Move zombies from right to left
    moveZombies();

    // Stop the game when the timer reaches 180 seconds
    if (tickCount >= 180) {
        stopGameTimer();  // Stop the game timer when the game ends
        System.out.println("Game Over! The timer has reached zero.");
    }
}



   private void moveZombies() {
    List<Zombie> zombiesToRemove = new ArrayList<>();  // To store zombies that go off-screen

    // Loop through each zombie and move it
    for (Zombie zombie : zombieList) {
        zombie.move();  // Move each zombie one step to the left

        // Check if zombie reaches the leftmost side (past column 0)
        if (zombie.getXPosition() < 0) {
            zombiesToRemove.add(zombie);  // Zombie has moved off the board to the left
            System.out.println("Zombie reached the left side at time: " + tickCount);
        }
    }

    // Remove zombies that have moved off-screen
    zombieList.removeAll(zombiesToRemove);
}





public void display() {
    // Clear the console for a fresh display (optional, for cleaner output)
    System.out.print("\033[H\033[2J");
    System.out.flush();

    // Print the board state
    for (int i = 0; i < 5; i++) {  // 5 rows
        System.out.println("---------------------------------------------");
        for (int j = 0; j < 9; j++) {  // 9 columns
            if (j == 0) System.out.print("| ");  // Start of row
            boolean occupied = false;

            // Check if any zombie is at this position
            for (Zombie zombie : zombieList) {
                if (zombie.getYPosition() == i && zombie.getXPosition() == j) {
                    occupied = true;  // Mark the position as occupied
                    break;
                }
            }

            // Print "Z" if occupied by a zombie, otherwise print "0"
            System.out.print((occupied ? "Z" : "0") + " | ");
        }
        System.out.println();
    }
    System.out.println("---------------------------------------------");
}







}
