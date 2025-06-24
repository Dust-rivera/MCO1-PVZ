public class GameTimer {
    private Timer timer;
    private Board board;

    public GameTimer(Board board) {
        this.board = board;
    }

    public void start() {
        timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            int count = 180;  // Game runs for 180 seconds

            @Override
            public void run() {
                System.out.print("\033[H\033[2J");
                System.out.flush();

                if (count < 0) {
                    System.out.println("Game done!");
                    timer.cancel();
                    return;
                }

                System.out.println("Time left: " + count + " seconds");
                board.display(); 
                System.out.println("Sun dropped: " + board.getSunCount());
                count--;
            }
        };

        // Schedule this to match 4 ticks = 1 second → 1000ms
        timer.scheduleAtFixedRate(timerTask, 0, 250);
    }
}
