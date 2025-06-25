import java.util.Scanner;

public class Driver {

    private boolean running = true;
    private static int timer = 180;
    private static int tickTimer = 0;
    private String message = "";
    private static int stringTick;

    public boolean getRunning() {
        return this.running;
    }

    public void setRunning(boolean bool) {
        running = bool;
    }

    public void setMessage(String string) {
        message = string;
    }

    public String getMessage() {
        return message;
    }

    public static void main(String[] args) {

        User user = new User();
        Driver driver = new Driver();
        Board board = new Board(user, driver);

        // Thread gameLoop = new Thread(() -> {
        // while (driver.getRunning()) {
        // System.out.println("Timer: " + timer + " seconds");
        // board.display(); // print current board
        // board.update(); // game logic: move zombies, shoot, etc.
        // tickTimer++;
        // try {
        // Thread.sleep(10); // 1-second tick
        // } catch (InterruptedException e) {
        // break;
        // }
        // System.out.print("\033[H\033[2J");
        // System.out.flush();

        // if(!driver.getMessage().equals("")) stringTick++;

        // if(stringTick == 4){
        // driver.setMessage("");
        // stringTick = 0;
        // }
        // if(tickTimer % 4 == 0) timer--;
        // if(timer == 0) driver.setRunning(false);
        // }
        // });

        Thread inputLoop = new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            while (driver.getRunning()) {
                if (scanner.hasNextLine()) {
                    String input = scanner.nextLine();
                    board.input(driver, board, input);
                }
            }
            scanner.close();
        });

        inputLoop.setDaemon(true);
        inputLoop.start();
        board.placePlant(0, 0, new Peashooter(0, 0));
        //board.placeZombie(0, 8, new Zombie());
        board.placePlant(1, 0, new Peashooter(0, 0));
        board.placePlant(2, 0, new Peashooter(0, 0));
        board.placePlant(3, 0, new Peashooter(0, 0));
        board.placePlant(4, 0, new Peashooter(0, 0));

        while (driver.getRunning()) {
            board.update();
            System.out.println("Timer: " + timer + " seconds");
            board.display(); // print current board
            // game logic: move zombies, shoot, etc.
            tickTimer++;
            try {
                Thread.sleep(250); // 1-second tick
            } catch (InterruptedException e) {
                break;
            }
            System.out.print("\033[H\033[2J");
            System.out.flush();

            if (!driver.getMessage().equals(""))
                stringTick++;

            if (stringTick == 4) {
                driver.setMessage("");
                stringTick = 0;
            }
            if (tickTimer % 4 == 0)
                timer--;
            if (timer == 0)
                driver.setRunning(false);
        }

        // gameLoop.start();
        // inputLoop.start();
        // gameLoop.join();
        // inputLoop.join();

        return;
    }
}