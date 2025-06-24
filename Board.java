import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {

    private Tile[][] board;
    private int tickCount = 0;
    private int secondsPassed = 0;
    private User player;
    private List<Zombie> zombieList;
    private int sunCount = 0;
    private boolean running = true;

    public Board(User player) {
        this.player = player;
        this.zombieList = new ArrayList<>();

        board = new Tile[5][9];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 9; j++) {
                board[i][j] = new Tile();
            }
        }
    }

    public void startGameLoop() {
    Thread gameThread = new Thread(() -> {
        while (secondsPassed < 180 && running) {
            tickCount++; // 

            // Update game logic
            update();

            // Then display results
            display();
            System.out.println("Sun dropped: " + sunCount);
            System.out.println("Time left: " + (180 - secondsPassed) + " seconds");

            try {
                Thread.sleep(250); // 1 tick = 250 ms
            } catch (InterruptedException e) {
                System.out.println("Game loop interrupted.");
                break;
            }

            System.out.print("\033[H\033[2J");
            System.out.flush();
        }

        System.out.println("Game Over!");
    });

    gameThread.start();
}


    public void stopGameLoop() {
        running = false;
    }

    public void generateSun() {
        sunCount++;
    }

   public void spawnZombie() {
    Random rand = new Random();
    int row = rand.nextInt(5); // 0 to 4
    Zombie zombie = new Zombie();
    zombie.setYPosition(row); // <-- This is important!
    placeZombie(row, 8, zombie);
    System.out.println("Zombie spawned at (" + row + ", 8) at time: " + secondsPassed);
}


    public void spawnWaveOfZombies() {
        Random rand = new Random();
        for (int i = 0; i < 10; i++) {
            int row = rand.nextInt(5);
            Zombie zombie = new Zombie();
            placeZombie(row, 8, zombie);
            System.out.println("Wave zombie spawned at (" + row + ", 8) at time: " + secondsPassed);
        }
    }

    public void placeZombie(int row, int col, Zombie zombie) {
        if (!board[row][col].isOccupied()) {
            board[row][col].setZombie(zombie);
            zombieList.add(zombie);
        }
    }

    public void placePlant(int row, int col, Plant plant) {
        if (!board[row][col].isOccupied()) {
            board[row][col].setPlant(plant);
            System.out.println("Placed plant at (" + row + ", " + col + ") ");
        } else {
            System.out.println("Tile (" + row + ", " + col + ") is already occupied.");
        }
    }

    public void moveZombies() {
    List<Zombie> zombiesToRemove = new ArrayList<>();

    for (Zombie zombie : zombieList) {
        int x = zombie.getXPosition();
        int y = zombie.getYPosition();

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
        if (x >= 0 && x < 9) {
            board[y][x].setZombie(null);
        }

        zombie.move(); // Move if no plant blocks

        int newX = zombie.getXPosition();
        if (newX == 0) {
            zombie.incrementTicksAtCol0();
            board[y][newX].setZombie(zombie);
            if (zombie.getTicksAtCol0() >= 4) {
                zombiesToRemove.add(zombie);
                System.out.println("Zombie left the screen after 4 ticks at column 0.");
            }
        } else if (newX > 0 && newX < 9) {
            board[y][newX].setZombie(zombie);
        }
    }

    zombieList.removeAll(zombiesToRemove);
}


    public void update() {
    tickCount++;

    // Every 1 second (every 4 ticks)
    if (tickCount % 4 == 0) {
        secondsPassed++;
        moveZombies();

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
        } else if (secondsPassed >= 171 && secondsPassed <= 180 && zombieList.isEmpty()) {
            spawnWaveOfZombies();
        }
    }
}




    public void display() {
        for (int i = 0; i < 5; i++) {
            System.out.println("---------------------------------------------");
            for (int j = 0; j < 9; j++) {
                if (j == 0) System.out.print("| ");

                Tile tile = board[i][j];
                String cell = "0";

                for (Zombie zombie : zombieList) {
                    if (zombie.getYPosition() == i && zombie.getXPosition() == j) {
                        cell = "Z";
                        break;
                    }
                }

                if (cell.equals("0") && tile.getPlant() != null) {
                    Plant plant = tile.getPlant();
                    if (plant instanceof Sunflower) {
                        cell = "S";
                    } else if (plant instanceof Peashooter) {
                        cell = "P";
                    } else if (plant instanceof CherryBomb) {
                        cell = "B";
                    }
                }

                System.out.print(cell + " | ");
            }
            System.out.println();
        }
        System.out.println("---------------------------------------------");
    }

    public int getSunCount() {
        return sunCount;
    }
}
