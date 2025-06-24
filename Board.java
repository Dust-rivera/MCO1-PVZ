import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Board {

    private Tile[][] board;
    private int tickCount = 0;
    private int secondsPassed = 0;  // Track the number of seconds passed
    private User player;
    private List<Zombie> zombieList;
    private int sunCount = 0;
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

        // Start the game timer
        startGameTimer();
    }

    // Start the game timer to update every 250 milliseconds (4 ticks per second)
    private void startGameTimer() {
        gameTimer = new Timer();

        // Schedule the game loop to run every 250 milliseconds (this will count as 1 tick)
        gameTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                update();  // Call the update method to handle game logic
            }
        }, 0, 250);  // 250 milliseconds = 1 tick
    }

    // Cancel the game timer when the game ends
    public void stopGameTimer() {
        if (gameTimer != null) {
            gameTimer.cancel();
            System.out.println("Game Over! Timer stopped.");
        }
    }

    // Generate sun every 40 ticks (1 second)
    public void generateSun() {
        sunCount++;
    }

    // Spawn a zombie at a random row, at the rightmost column
    public void spawnZombie() {
        Random rand = new Random();
        int row = rand.nextInt(5);  // Random row (0-4)
        Zombie zombie = new Zombie();
        placeZombie(row, 8, zombie);  // Spawn zombie at the last column (rightmost)
        System.out.println("Zombie spawned at (" + row + ", 8) at time: " + secondsPassed);
    }

    // Spawn wave of zombies from 171 to 180 seconds
    public void spawnWaveOfZombies() {
        Random rand = new Random();
        for (int i = 0; i < 10; i++) {  // Wave of 10 zombies
            int row = rand.nextInt(5);  // Random row (0-4)
            Zombie zombie = new Zombie();
            placeZombie(row, 8, zombie);  // Place zombies at the last column
            System.out.println("Wave zombie spawned at (" + row + ", 8) at time: " + secondsPassed);
        }
    }

    // Place a zombie at the specified row and column
    public void placeZombie(int row, int col, Zombie zombie) {
        if (!board[row][col].isOccupied()) {
            board[row][col].setZombie(zombie);
            zombieList.add(zombie);
        }
    }

    // Place a plant on the board at the specified row and column
    public void placePlant(int row, int col, Plant plant) {
        if (!board[row][col].isOccupied()) {
            board[row][col].setPlant(plant);
            System.out.println("Placed plant at (" + row + ", " + col ")");
        } else {
            System.out.println("Tile (" + row + ", " + col + ") is already occupied.");
        }
    }

    // Move zombies from right to left every 4 ticks (1 second)
    public void moveZombies() {
        List<Zombie> zombiesToRemove = new ArrayList<>();
        for (Zombie zombie : zombieList) {
            zombie.move();  // Move each zombie one step to the left

            // Check if zombie reaches the leftmost side (past column 0)
            if (zombie.getXPosition() < 0) {
                zombiesToRemove.add(zombie);  // Zombie has moved off the board to the left
                System.out.println("Zombie reached the left side.");
            }
        }
        zombieList.removeAll(zombiesToRemove);  // Remove zombies that moved off-screen
    }

    // Update the board based on the timer and game logic
    public void update() {
        tickCount++;

        // Every 40 ticks (1 second), generate sun
        if (tickCount % 40 == 0) {
            generateSun();
        }

        // Spawn zombies based on the timer intervals
        if (secondsPassed >= 30 && secondsPassed <= 80 ) {  // Every 10 seconds (40 ticks)
            spawnZombie();
        } else if (secondsPassed >= 81 && secondsPassed <= 140 ) {  // Every 5 seconds (20 ticks)
            spawnZombie();
        } else if (secondsPassed >= 141 && secondsPassed <= 170) {  // Every 3 seconds (12 ticks)
            spawnZombie();
        } else if (secondsPassed >= 171 && secondsPassed <= 180 && zombieList.isEmpty()) {
            spawnWaveOfZombies();  // Wave of zombies
        }

        // Move zombies every 4 ticks (1 second)
        if (tickCount % 4 == 0) {
            moveZombies();
        }

        // Increment secondsPassed every 4 ticks (1 second)
        if (tickCount % 4 == 0) {
            secondsPassed++;
        }

        // Stop the game when the timer reaches 180 seconds
        if (secondsPassed >= 180) {
            stopGameTimer();  // Stop the game timer when the game ends
            System.out.println("Game Over! The timer has reached zero.");
        }
    }

    // Display the current state of the board
    public void display() {
        System.out.print("\033[H\033[2J");
        System.out.flush();

        for (int i = 0; i < 5; i++) {  // 5 rows
            System.out.println("---------------------------------------------");
            for (int j = 0; j < 9; j++) {  // 9 columns
                if (j == 0) System.out.print("| ");  // Start of row
                boolean occupied = false;

                // Check if any zombie is at this position
                for (Zombie zombie : zombieList) {
                    if (zombie.getYPosition() == i && zombie.getXPosition() == j) {
                        occupied = true;
                        break;
                    }
                }

                System.out.print((occupied ? "Z" : "0") + " | ");
            }
            System.out.println();
        }
        System.out.println("---------------------------------------------");
    }

    public int getSunCount() {
        return sunCount;
    }
}
