public class Tile {

    private Plant plant = null;
    private Zombie zombie = null;

    public Plant getPlant() {
        return plant;
    }

    public Zombie getZombie() {
        return zombie;
    }

    public void setPlant(Plant plant) {
        this.plant = plant;
    }

    public void setZombie(Zombie zombie) {
        this.zombie = zombie;
    }

    public boolean isOccupied(){
        return (plant != null || zombie != null);
    }

    public void removeZombie(){
        this.zombie = null;
    }
}
