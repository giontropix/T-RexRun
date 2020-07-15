public class Character {
    enum Species {TREX, LITTLEPALM, BIGPALM, BIRD}
    private final Species species;
    private final int height;

    public Character(Species species, int height){
        this.height = height;
        this.species = species;
    }

    public Species getSpecies() {
        return species;
    }

    public int getHeight() {
        return height;
    }
}
