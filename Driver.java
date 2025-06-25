import java.util.Scanner;

public class Driver {

    public static void main(String[] args) {

        int timer = 180;
        int tickTimer = 0;
        int stringTick = 0;

        User user = new User();
        Board board = new Board(user);

        Thread inputLoop = new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            while (board.getRunning()) {
                if (scanner.hasNextLine()) {
                    String input = scanner.nextLine();
                    board.input(board, input);
                }
            }
            scanner.close();
        });

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
                Thread.sleep(250);
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
                    && !board.getfinalWaveFlag()) {
                board.setRunning(false);
                System.out.println("GAME WON!!!!!");
            }

        }
    }
}