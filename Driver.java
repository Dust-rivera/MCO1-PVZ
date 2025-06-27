import java.util.Scanner;

public class Driver {

    public static void main(String[] args) {

        int timer = 180;
        int tickTimer = 0;
        int stringTick = 0;

        User user = new User();
        Board board = new Board(user, 5, 9);
        Scanner scanner = new Scanner(System.in);

        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("Welcome to Plants vs Zombies!!\n");
        System.out.println("This game continuously updates 4 times per second");
        System.out.println("There are " + board.getRows() + " rows and " + board.getCol() + " columns");
        System.out.println("COMMANDS[not case-sensitive]: ");
        System.out.println("\tc - Collects Sun");
        System.out.println("\ts [row] [column] - Plants Sunflower at Specific Coordinate");
        System.out.println("\tp [row] [column] - Plants Peashooter at Specific Coordinate\n");
        System.out.println("GOOD LUCK AND HAVE FUN\n");
        System.out.println("Press any key to continue...");
        scanner.nextLine();
        System.out.print("\033[H\033[2J");
        System.out.flush();
        
        Thread inputLoop = new Thread(() -> {
            while (board.getRunning()) {
                if (scanner.hasNextLine()) {
                    user.setInput(scanner.nextLine());
                    board.input(board);
                }
            }
            scanner.close();
        });

        board.placePlant(0, 0, new Peashooter(0, 0));
        board.placePlant(1, 0, new Peashooter(0, 1));
        board.placePlant(2, 0, new Peashooter(0, 2));
        board.placePlant(3, 0, new Peashooter(0, 3));
        board.placePlant(4, 0, new Peashooter(0, 4));

        board.placePlant(0, 1, new Peashooter(1, 0));
        board.placePlant(1, 1, new Peashooter(1, 1));
        board.placePlant(2, 1, new Peashooter(1, 2));
        board.placePlant(3, 1, new Peashooter(1, 3));
        board.placePlant(4, 1, new Peashooter(1, 4));

        board.placePlant(0, 2, new Peashooter(2, 0));
        board.placePlant(1, 2, new Peashooter(2, 1));
        board.placePlant(2, 2, new Peashooter(2, 2));
        board.placePlant(3, 2, new Peashooter(2, 3));
        board.placePlant(4, 2, new Peashooter(2, 4));

        board.placePlant(0, 3, new Peashooter(3, 0));
        board.placePlant(1, 3, new Peashooter(3, 1));
        board.placePlant(2, 3, new Peashooter(3, 2));
        board.placePlant(3, 3, new Peashooter(3, 3));
        board.placePlant(4, 3, new Peashooter(3, 4));


        inputLoop.setDaemon(true);
        inputLoop.start();

        while (board.getRunning()) {

            board.update();
            if (!board.getRunning())
                break;
            System.out.println("Timer: " + (timer) + " seconds");

            tickTimer++;

            board.display();

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                break;
            }
            System.out.print("\033[H\033[2J");
            System.out.flush();

            if (!board.getMessage().equals(""))
                stringTick++;

            if (stringTick == 4) {
                board.setMessage("");
                stringTick = 0;
            }
            if (tickTimer % 4 == 0)
                timer--;

            if (timer == -1) {
                board.setRunning(false);
                System.out.println("GAME WON!!!!!");
            }else if (timer <= 10
                    && board.getZombieList().isEmpty()
                    && !board.getFinalWaveFlag()) {
                board.setRunning(false);
                System.out.println("GAME WON!!!!!");
            }

        }
    }
}