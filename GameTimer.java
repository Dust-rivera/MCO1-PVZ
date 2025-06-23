import java.util.Timer;
import java.util.TimerTask;

public class GameTimer {
    private Timer timer;
    private Board board;

    public GameTimer(Board board) {
        this.board = board;
    }

    public void start() {
        timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            int count = 180;

            @Override
            public void run() {
                System.out.print("\033[H\033[2J");
                System.out.flush();

                if (count < 0) {
                    System.out.println("Game done!");
                    timer.cancel();
                }

                System.out.println("Time left: " + count + " seconds");
                
                board.display(); 
                count--;
            }
        };

        timer.scheduleAtFixedRate(timerTask, 0, 500);// TICKS EVERY SECOND
    }
}
