public class Driver {
    public static void main(String[] args){
        Board board = new Board();

        Sunflower sunflower = new Sunflower();

        board.placePlant(1, 1, sunflower);

        //System.out.println(board);
    }
}
