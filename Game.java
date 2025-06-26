import java.util.Scanner;

/**
 * Represents the main game logic and loop for the Plants vs. Zombies game.
 * This class encapsulates timer control, board updates, and input handling.
 * 
 * @author Deveza, Jerry King
 * @author Rivera, Dustine Gian
 * @version 1.0
 */
public class Game {
    private int timer;
    private int tickTimer;
    private int stringTick;
    private User user;
    private Board board;
    private Thread inputLoop;

    /**
     * Constructs a Game object with default timer and initializes user and board.
     */
    public Game() {
        this.timer = 180;
        this.tickTimer = 0;
        this.stringTick = 0;
        this.user = new User();
        this.board = new Board(user);
    }

    /**
     * Starts the game loop and handles game progression, user input, and win conditions.
     */
    public void start() {
        setupInputThread();
        inputLoop.setDaemon(true);
        inputLoop.start();

        while (board.getRunning()) {
            board.update();
            if (!board.getRunning()) break;

            System.out.println("Timer: " + timer + " seconds");
            tickTimer++;
            board.display();

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                break;
            }

            System.out.print("\033[H\033[2J");
            System.out.flush();

            handleMessageCycle();
            decrementTimer();
            checkWinCondition();
        }
    }

    /**
     * Sets up a separate thread to continuously read user input
     * and forward it to the board for command processing.
     */
    private void setupInputThread() {
        inputLoop = new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            while (board.getRunning()) {
                if (scanner.hasNextLine()) {
                    user.setInput(scanner.nextLine());
                    board.input(board);
                }
            }
            scanner.close();
        });
    }

    /**
     * Handles message timing logic — clears temporary messages
     * after a certain number of ticks.
     */
    private void handleMessageCycle() {
        if (!board.getMessage().equals("")) {
            stringTick++;
        }
        if (stringTick == 4) {
            board.setMessage("");
            stringTick = 0;
        }
    }

    /**
     * Decreases the game timer every 4 ticks.
     */
    private void decrementTimer() {
        if (tickTimer % 4 == 0) {
            timer--;
        }
    }

    /**
     * Checks whether win conditions have been met,
     * such as timer ending or all zombies cleared.
     * Ends the game if conditions are satisfied.
     */
    private void checkWinCondition() {
        if (timer == -1) {
            board.setRunning(false);
            System.out.println("GAME WON!!!!!");
        } else if (timer <= 10 && board.getZombieList().isEmpty() && !board.getfinalWaveFlag()) {
            board.setRunning(false);
            System.out.println("GAME WON!!!!!");
        }
    }
}
