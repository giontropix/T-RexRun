import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class FieldOfObstacle extends Thread {
    private final int fieldHeight = 10;
    private final int fieldWidth = 100;
    private boolean isInGame = true;
    private int score = 0;
    private int distance = 0;
    private final ArrayList<Coordinate> palm = new ArrayList<>();
    private final ArrayList<Coordinate> bird = new ArrayList<>();
    private final ArrayList<Coordinate> ground = new ArrayList<>();

    public FieldOfObstacle(){
        this.generateObstacle();
    }

    @Override
    public void run() {
        try {
            do {
                this.calculateScore();
                this.generateObstacle();
                this.moveObstacle();
                this.distance++;
                Thread.sleep (1500);
            } while(this.isInGame());
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Coordinate> getPalm() {
        return palm;
    }

    public ArrayList<Coordinate> getBird() {
        return bird;
    }

    public ArrayList<Coordinate> getGround() {
        return ground;
    }

    public int getFieldHeight() {
        return this.fieldHeight;
    }

    public int getFieldWidth() {
        return this.fieldWidth;
    }

    public int getScore() {
        return score;
    }

    public int getDistance() {
        return distance;
    }

    public boolean isInGame() {
        return this.isInGame;
    }

    public void setInGame(boolean inGame) {
        this.isInGame = inGame;
    }

    private void generateObstacle(){
        double random = Math.random();
        this.ground.add(new Coordinate(this.fieldHeight, this.fieldWidth - 1));
        if(random < 0.1) { // probability to add a big palm
            this.palm.add(new Coordinate(this.fieldHeight - 1, this.fieldWidth - 1));
            this.palm.add(new Coordinate(this.fieldHeight - 2, this.fieldWidth - 1));
        }
        else if(random >= 0.1 && random <= 0.2) //probability to add a little palm
            this.palm.add(new Coordinate(this.fieldHeight - 1, this.fieldWidth - 1));
        else if(random > 0.2 && random <= 0.25) { //probability to add a bird
            int randomHeight = ThreadLocalRandom.current().nextInt(3, this.fieldHeight - 1);
            this.bird.add(new Coordinate(randomHeight, this.fieldWidth - 1));
        }
    }

    private void moveObstacle(){
        for (Coordinate coordinate : this.palm) {
            coordinate.setY(coordinate.getY() - 1);
        }
        for (Coordinate coordinate : this.bird) {
            coordinate.setY(coordinate.getY() - 1);
        }
        for (Coordinate coordinate : this.ground) {
            coordinate.setY(coordinate.getY() - 1);
        }
    }

    public void calculateScore() {
        if (this.isInGame()) {
            if (this.palm.contains(new Coordinate(this.getFieldHeight() - 1, 4)))
                this.score += 10;
            if (this.palm.contains(new Coordinate(this.getFieldHeight() - 2, 4)))
                this.score += 10;
        }
    }
}