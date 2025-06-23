import java.util.Scanner;

public class Driver {

    private static boolean running = true;
    private static int timer = 180;
    public static void main(String[] args){

        User user = new User();
        Board board = new Board(user);
        //Sunflower sunflower = new Sunflower();

        Thread gameLoop = new Thread(() -> {
            while (running) {
                //board.update(); // game logic: move zombies, shoot, etc.
                System.out.println("Timer: " + timer-- + " seconds");
                board.display(); // print current board
                System.out.println("Sun Points: " + user.getSunCount());
                System.out.print("Enter comamnd: ");
                try {
                    Thread.sleep(1000); // 1-second tick
                } catch (InterruptedException e) {
                    break;
                }
                System.out.print("\033[H\033[2J");
                System.out.flush();

                if(timer == 0)
                    running = false;
            }
        });

        Thread inputLoop = new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            while(running){
                
                String input = scanner.nextLine();

                if(input.equalsIgnoreCase("exit")){
                    running = false;
                }else if(input.startsWith("plant")){
                    String[] coordinate = input.split(" ");
                    board.placePlant(0, 0, new Sunflower());
                }

            }
            scanner.close();
        });

        gameLoop.start();
        inputLoop.start();





        // board.placePlant(1, 1, sunflower);

        // System.out.println(board);
        // Zombie zombie = new Zombie();
        // board.placeZombie(2, 8, zombie);
        // board.placePlant(0, 0, sunflower);
        // board.display();
        //System.out.println(board);
    }
}