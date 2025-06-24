import java.util.Scanner;

public class Driver {

    private static boolean running = true;
    private static int timer = 180;
    private static int tickTimer = 0;

    public static void main(String[] args) {

        User user = new User();
        Board board = new Board(user);

        Thread gameLoop = new Thread(() -> {
            while (running) {
                board.update(); // game logic: move zombies, shoot, etc.
                System.out.println("Timer: " + timer + " seconds");
                board.display(); // print current board
                System.out.println("Sun dropped: " + board.getSunCount());
                System.out.println("Sun Points: " + user.getSunCount());
                System.out.print("Enter command: ");
                tickTimer++;

                // Every second
                if (tickTimer % 4 == 0) {
                    timer--;

                    // Decrease plant cooldowns
                    if (Plant.sunflowerCD > 0) Plant.sunflowerCD--;
                    if (Plant.peashooterCD > 0) Plant.peashooterCD--;
                    if (Plant.cherryBombCD > 0) Plant.cherryBombCD--;
                }

                if (timer == 0)
                    running = false;

                try {
                    Thread.sleep(250); // 1 tick = 250ms
                } catch (InterruptedException e) {
                    break;
                }

                System.out.print("\033[H\033[2J");
                System.out.flush();
            }

            System.out.println("Game Over!");
        });

        Thread inputLoop = new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            while (running) {
                String input = scanner.nextLine();

                if (input.equalsIgnoreCase("exit")) {
                    running = false;
                } else if (input.startsWith("sunflower")) {
                    String[] coordinate = input.split(" ");
                    int row = Integer.parseInt(coordinate[1]);
                    int col = Integer.parseInt(coordinate[2]);
                    if (user.getSunCount() >= 50 && Plant.sunflowerCD == 0) {
                        Plant.sunflowerCD = Plant.SUNFLOWER_CD;
                        board.placePlant(row, col, new Sunflower(row, col));
                        user.buyPlant(50);
                    } else if (user.getSunCount() >= 50) {
                        System.out.println("Sunflower on cooldown!");
                    } else {
                        System.out.println("Not enough sun!");
                    }
                } else if (input.startsWith("peashooter")) {
                    String[] coordinate = input.split(" ");
                    int row = Integer.parseInt(coordinate[1]);
                    int col = Integer.parseInt(coordinate[2]);
                    if (user.getSunCount() >= 100 && Plant.peashooterCD == 0) {
                        Plant.peashooterCD = Plant.PEASHOOTER_CD;
                        board.placePlant(row, col, new Peashooter(row, col));
                        user.buyPlant(100);
                    } else if (user.getSunCount() >= 100) {
                        System.out.println("Peashooter on cooldown!");
                    } else {
                        System.out.println("Not enough sun!");
                    }
                } else if (input.equalsIgnoreCase("collect")) {
                    user.collectSun(board.getSunCount(), board);
                }
            }
            scanner.close();
        });

        gameLoop.start();
        inputLoop.start();

        
        board.placePlant(0, 1, new Sunflower(1, 1));
         board.placeZombie(0, 8, new Zombie());
         board.placePlant(0, 2, new Peashooter(1, 1));
    }
}
