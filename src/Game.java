import java.util.ArrayList;

public class Game extends Thread {
    private int heightField;
    private int weightField;
    private boolean isInGame = true;
    ArrayList<Character> palm = new ArrayList<>();

    public Game(int landscape){
        this.heightField = 10;
        this.weightField = landscape;
    }

    public int getHeightField() {
        return heightField;
    }

    public int getWeightField() {
        return weightField;
    }

    @Override
    public void run() {
        try {
            while(isInGame()){
                Thread.sleep (600);
                this.generatePalm();
                this.moveObstacle();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isInGame() {
        return this.isInGame;
    }

    private void generatePalm(){
        double random = Math.random();
        if(random <= 0.1) {
            palm.add(new Character(heightField - 1, weightField));
            palm.add(new Character(heightField - 2, weightField));
        }
        else if(random <= 0.2)
            palm.add(new Character(heightField - 1, weightField));
    }

    private void moveObstacle(){
        for (Character character : palm) {
            character.setY(character.getY() - 1);
        }
    }

    /*public String generateGround(){
        double random = Math.random();
        if(random <= 0.25)
            return "*";
        else
            return "_";
    }*/
}
