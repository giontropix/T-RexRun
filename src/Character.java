public class Character {
    private final String name;
    private final int height;

    public Character(String name, int height){
        this.height = height;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getHeight() {
        return height;
    }
}
