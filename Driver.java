public class Driver {
    public static void main(String[] args){
        Board board = new Board();
        Sunflower sunflower = new Sunflower();
        Zombie zombie = new Zombie();
        board.placeZombie(2, 8, zombie);
        board.placePlant(0, 0, sunflower);
        System.out.println(board);
    }
}
