public class Tile {

    private Plant plant;
    private Zombie zombie;

    public boolean isOccupied(){
        return (plant != null || zombie != null);
    }

    public void setPlant(Plant plant) {
        this.plant = plant;
    }

    public void setZombie(Zombie zombie) {
        this.zombie = zombie;
    }

    public Plant getPlant() {
        return plant;
    }

    public Zombie getZombie() {
        return zombie;
    }

    public static void main(String[] args){

        Tile tile = new Tile();
        Sunflower sunflow = new Sunflower();
        tile.setPlant(sunflow);

        System.out.println(tile.isOccupied());
    }
}
