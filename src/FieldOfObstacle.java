import java.util.ArrayList;

public class FieldOfObstacle extends Thread {
    private final int heightField = 10;
    private final int weightField;
    private boolean isInGame = true;
    ArrayList<Coordinate> palm = new ArrayList<>();

    public FieldOfObstacle(int landscape){
        this.weightField = landscape;
    }

    public int getHeightField() {
        return this.heightField;
    }

    public int getWeightField() {
        return this.weightField;
    }

    @Override
    public void run() {
        try {
            while(isInGame()){
                Thread.sleep (1000);
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
            this.palm.add(new Coordinate(this.heightField - 1, this.weightField));
            this.palm.add(new Coordinate(this.heightField - 2, this.weightField));
        }
        else if(random > 0.1 && random <= 0.2)
            this.palm.add(new Coordinate(this.heightField - 1, this.weightField));
    }

    private void moveObstacle(){
        for (Coordinate coordinate : this.palm) {
            coordinate.setY(coordinate.getY() - 1);
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