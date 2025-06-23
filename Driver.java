public class Driver {

    private static boolean running = true;
    private static int timer = 180; // Game timer (180 seconds)

    public static void main(String[] args) {

        // Create user and board objects
        User user = new User();
        Board board = new Board(user);

        // Game loop for updating the board and timer
        Thread gameLoop = new Thread(() -> {
            while (running) {
                // Update the board (move zombies, spawn zombies, etc.)
                board.update();  // game logic: move zombies, spawn them, etc.

                // Display the current board state and timer
                System.out.println("Timer: " + timer-- + " seconds");
                board.display();  // print current board
                System.out.println("Sun Points: " + user.getSunCount());

                // Wait for 1 second before the next update
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    break;
                }

                // Clear the console for a cleaner display
                System.out.print("\033[H\033[2J");
                System.out.flush();

                // End the game if the timer reaches zero
                if (timer == 0) {
                    running = false;
                }
            }
        });

        // Start the game loop thread
        gameLoop.start();
    }
}
