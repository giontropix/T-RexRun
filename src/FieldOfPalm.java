import java.util.ArrayList;

public class FieldOfPalm extends Thread {
    private final int fieldHeight = 10;
    private final int fieldWidth = 100;
    private boolean isInGame = true;
    ArrayList<Coordinate> palm = new ArrayList<>();

    public FieldOfPalm(){
        this.generatePalm();
    }

    public int getFieldHeight() {
        return this.fieldHeight;
    }

    public int getFieldWidth() {
        return this.fieldWidth;
    }

    @Override
    public void run() {
        try {
            while(this.isInGame()){
                this.generatePalm();
                this.moveObstacle();
                Thread.sleep (1500);
            }
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean isInGame() {
        return this.isInGame;
    }

    public void setInGame(boolean inGame) {
        this.isInGame = inGame;
    }

    private void generatePalm(){
        double random = Math.random();
        if(random < 0.1) {
            this.palm.add(new Coordinate(this.fieldHeight - 1, this.fieldWidth - 1));
            this.palm.add(new Coordinate(this.fieldHeight - 2, this.fieldWidth - 1));
        }
        else if(random >= 0.1 && random <= 0.2)
            this.palm.add(new Coordinate(this.fieldHeight - 1, this.fieldWidth - 1));
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