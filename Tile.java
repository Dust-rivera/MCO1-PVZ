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

    public void removeZombie(){
        this.zombie = null;
    }

    public static void main(String[] args){

        Tile tile = new Tile();
        //Sunflower sunflower = new Sunflower();
        //tile.setPlant(sunflower);

        System.out.println(tile.isOccupied());
    }
}
