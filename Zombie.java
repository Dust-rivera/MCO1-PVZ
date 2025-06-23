public class Zombie {
    private int xPosition;  // Horizontal position on the board (starts from 8 to 0)
    private int yPosition;  // Vertical position (row) on the board

    // Default constructor sets the starting position
    public Zombie() {
        this.xPosition = 8;  // Start at the rightmost position (column 8)
        this.yPosition = (int) (Math.random() * 5);  // Random row for simplicity (0 to 4)
    }

    // Move the zombie to the left by 1 tile
    public void move() {
        if (this.xPosition > 0) {  // Ensure the zombie doesn't move past the leftmost side
            this.xPosition--;  // Move one step to the left (decrease xPosition)
        }
    }

    // Get the zombie's current xPosition (horizontal position)
    public int getXPosition() {
        return xPosition;
    }

    // Get the zombie's current yPosition (row position)
    public int getYPosition() {
        return yPosition;
    }
}
