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
    private boolean finalWaveFlag = true;
    private String message = "";
    private boolean running = true;

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

    public int getSunCount() {
        return sunCount;
    }

    public List<Zombie> getZombieList() {
        return zombieList;
    }

    public boolean getfinalWaveFlag() {
        return finalWaveFlag;
    }

    public String getMessage(){
        return message;
    }
    
    public boolean getRunning(){
        return running;
    }

    public void setMessage(String string){
        message = string;
    }

    public void setRunning(boolean bool){
        running = bool;
    }

    public void input(Board board, String input) {
        if (input.equalsIgnoreCase("exit")) {
            this.setRunning(false);
        } else if (input.startsWith("S") || input.startsWith("s")) {
            String[] coordinate = input.split(" ");
            int row = Integer.parseInt(coordinate[1]);
            int col = Integer.parseInt(coordinate[2]);
            if (player.getSunCount() >= 50 && Plant.sunflowerCD == 0) {
                Plant.sunflowerCD = Plant.SUNFLOWER_CD;
                board.placePlant(row, col, new Sunflower(row, col));
            } else if (player.getSunCount() >= 50 && Plant.sunflowerCD != 0) {
                this.setMessage("Sunflower on cooldown!");
            } else {
                this.setMessage("Not enough Sun!");

            }
            coordinate = null;
        } else if (input.startsWith("P") || input.startsWith("p")) {
            String[] coordinate = input.split(" ");
            int row = Integer.parseInt(coordinate[1]);
            int col = Integer.parseInt(coordinate[2]);
            if (player.getSunCount() >= 100 && Plant.peashooterCD == 0) {
                Plant.peashooterCD = Plant.PEASHOOTER_CD;
                board.placePlant(row, col, new Peashooter(row, col));
            } else if (player.getSunCount() >= 100 && Plant.peashooterCD != 0) {
                this.setMessage("Peashooter on cooldown!");
            } else {
                this.setMessage("Not enough Sun!");
            }
        } else if (input.equalsIgnoreCase("c")) {
            if (board.getSunCount() == 0) {
                this.setMessage("No sun");
            } else {
                this.setMessage("Sun collected");
                player.collectSun(board.getSunCount(), board);
            }
        }
    }

    public void moveZombies() {
        List<Zombie> zombiesToRemove = new ArrayList<>();

        for (Zombie zombie : zombieList) {
            int x = zombie.getXPosition();
            int y = zombie.getYPosition();
            Tile tile = board[y][x];

            if (x == 0) {
                zombiesToRemove.add(zombie);
                System.out.println("\nZombie has entered your home! \nGAME OVER");
                this.setRunning(false);
                break;
            }

            if (tile.getPlant() == null && board[y][x - 1].getZombie() == null) {
                zombie.move();
                board[y][x].setZombie(null);
                board[y][x - 1].setZombie(zombie);
            }

            if (zombie.isDead()) {
                board[y][x].setZombie(null);
                zombiesToRemove.add(zombie);
                this.setMessage("Zombie at (" + y + ", " + x + ") died and was removed.");
                continue;
            }
            if (x >= 0 && x < 9) {
                Plant plant = tile.getPlant();

                if (plant != null) {
                    zombie.incrementAttackTick();
                    plant.decreaseHealth(zombie.getDamage());
                    zombie.resetAttackTick();
                    this.setMessage(
                            "Zombie at (" + y + ", " + x + ") attacked plant: " + plant.getHealth() + " HP left");

                    if (plant.isDead()) {
                        tile.setPlant(null);
                        plant = null;
                        System.out.println("Plant at (" + y + ", " + x + ") died.");
                    }
                    continue;
                }
            }

        }

        zombieList.removeAll(zombiesToRemove);
    }

    public void generateSun() {
        sunCount++;
    }

    public void spawnZombie() {
        Random rand = new Random();
        int row;
        do {
            row = rand.nextInt(5);
        } while (board[row][8].isOccupied());
        Zombie zombie = new Zombie(row);
        zombie.setYPosition(row);
        placeZombie(row, 8, zombie); 
        if (!(secondsPassed >= 171 && secondsPassed <= 180)) {
            this.setMessage("Zombie spawned at (" + row + ", 8) at time: " + secondsPassed);
        }

    }

    public void spawnWaveOfZombies() {
        for (int i = 0; i < 5; i++) {
            spawnZombie(); 
        }
    }

    public void placeZombie(int row, int col, Zombie zombie) {
        if (!board[row][col].isOccupied()) {
            board[row][col].setZombie(zombie);
            zombieList.add(zombie);
        } else {
            spawnZombie();
        }
    }

    public void placePlant(int row, int col, Plant plant) {
        if (!board[row][col].isOccupied()) {
            board[row][col].setPlant(plant);
            player.buyPlant(plant.getCost());
            this.setMessage("Placed plant at (" + row + ", " + col + ")");
        } else {
            this.setMessage("Tile (" + row + ", " + col + ") is already occupied.");
        }
    }

    public void update() {
        tickCount++;

        if (tickCount % 40 == 0) {
            generateSun();
        }

        if (tickCount % 4 == 0) {
            secondsPassed++;

            if (secondsPassed % 10 == 0) {
                generateSun();
            }

            if (secondsPassed >= 30 && secondsPassed <= 80 && secondsPassed % 10 == 0) {
                spawnZombie();
            } else if (secondsPassed >= 81 && secondsPassed <= 140 && secondsPassed % 5 == 0) {
                spawnZombie();
            } else if (secondsPassed >= 141 && secondsPassed <= 170 && secondsPassed % 3 == 0) {
                spawnZombie();
            } else if (secondsPassed >= 171 && secondsPassed < 180 && !board[0][8].isOccupied()
                    && !board[1][8].isOccupied() && !board[2][8].isOccupied() && !board[3][8].isOccupied()
                    && !board[4][8].isOccupied() && finalWaveFlag) {
                finalWaveFlag = false;
                this.setMessage("A wave of zombies has appeared");
                spawnWaveOfZombies();
            }
        }

        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
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

    public void display() {
        for (int i = 0; i < 5; i++) {
            System.out.println("---------------------------------------------");
            for (int j = 0; j < 9; j++) {
                if (j == 0)
                    System.out.print("| ");

                Tile tile = board[i][j];
                String cell = "0";

                if (tile.getZombie() != null) {
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
        System.out.println("Game message: " + this.getMessage());
        System.out.println("Sun dropped: " + this.getSunCount());
        System.out.println("Sun Points: " + player.getSunCount());
        System.out.print("Enter comamnd: ");
    }

    public void setSun() {
        sunCount = 0;
    }

    public int getRows() {
        return board.length;
    }

    public int getCol() {
        return board[0].length;
    }

    public Tile getTile(int row, int col) {
        return board[row][col];
    }

    public int getTileRow(Tile target) {
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                if (board[r][c] == target) {
                    return r;
                }
            }
        }
        return -1;
    }

    public int getTileCol(Tile target) {
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                if (board[r][c] == target) {
                    return c;
                }
            }
        }
        return -1;
    }
}