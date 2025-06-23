import java.util.Scanner;

public class Driver {

    private static boolean running = true;
    private static int timer = 180;
    private static int tickTimer = 0;
    public static void main(String[] args){

        User user = new User();
        Board board = new Board(user);
        //Sunflower sunflower = new Sunflower();

        Thread gameLoop = new Thread(() -> {
            while (running) {
                board.update(); // game logic: move zombies, shoot, etc.
                System.out.println("Timer: " + timer + " seconds");
                board.display(); // print current board
                System.out.println("Sun dropped: " + board.getSunCount());
                System.out.println("Sun Points: " + user.getSunCount());
                System.out.print("Enter comamnd: ");
                tickTimer++;
                try {
                    Thread.sleep(250); // 1-second tick
                } catch (InterruptedException e) {
                    break;
                }
                System.out.print("\033[H\033[2J");
                System.out.flush();

                if(tickTimer % 4 == 0){
                    timer--;
                }

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
                }else if(input.startsWith("sunflower")){
                    String[] coordinate = input.split(" ");
                    int row = Integer.parseInt(coordinate[1]);
                    int col = Integer.parseInt(coordinate[2]);
                    if(user.getSunCount() >= 50 && Plant.sunflowerCD == 0){
                        Plant.sunflowerCD = Plant.SUNFLOWER_CD;
                        board.placePlant(row, col, new Sunflower(row, col));
                        user.buyPlant(50);
                    }else if(user.getSunCount() >= 50 && Plant.sunflowerCD != 0){
                        System.out.println("Sunflower on cooldown!");
                    }else{
                        System.out.println("Not enough sun!");
                    }
                    coordinate = null;
                }else if(input.startsWith("peashooter")){
                    String[] coordinate = input.split(" ");
                    int row = Integer.parseInt(coordinate[1]);
                    int col = Integer.parseInt(coordinate[2]);
                    if(user.getSunCount() >= 100 && Plant.peashooterCD == 0){
                        Plant.peashooterCD = Plant.PEASHOOTER_CD;
                        board.placePlant(row, col, new Peashooter(row, col));
                        user.buyPlant(100);
                    }else if(user.getSunCount() >= 100 && Plant.peashooterCD != 0){
                        System.out.println("Peashooter on cooldown!");
                    }else{
                        System.out.println("Not enough sun!");
                    }
                }else if(input.equalsIgnoreCase("collect")){
                    user.collectSun(board.getSunCount(), board);
                }

            }
            scanner.close();
        });

        gameLoop.start();
        inputLoop.start();
        board.placeZombie(0, 8, new Zombie());
        if(timer == 0){
            running = false;
        }





        // board.placePlant(1, 1, sunflower);

        // System.out.println(board);
        // Zombie zombie = new Zombie();
        // board.placeZombie(2, 8, zombie);
        // board.placePlant(0, 0, sunflower);
        // board.display();
        //System.out.println(board);
    }
}