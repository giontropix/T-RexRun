import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;

public class FieldOfObstacle extends Thread {
    private final int fieldHeight = 8;
    private final int fieldWidth = 25;
    private boolean isInGame = true;
    private int score = 0;
    private final Vector<Coordinate> palm = new Vector<>();
    private final Vector<Coordinate> bird = new Vector<>();
    private final Vector<Coordinate> ground = new Vector<>();

    public FieldOfObstacle(){
        this.generateGround();
    }

    @Override
    public void run() {
        try {
            do {
                this.generateObstacle();
                this.moveObstacle();
                this.score++;
                this.calculateScoreFromObstacle();
                this.deleteOutOfViewObstacle();
                Thread.sleep (this.increaseSpeedAccordingToPoints());
            } while(this.isInGame);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Vector<Coordinate> getPalm() {
        return palm;
    }

    public Vector<Coordinate> getBird() {
        return bird;
    }

    public Vector<Coordinate> getGround() {
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

    public boolean isInGame() {
        return this.isInGame;
    }

    public void setInGame(boolean inGame) {
        this.isInGame = inGame;
    }

    public void generateGround(){
        for (int i = 1; i < this.fieldWidth - 1; i++) {
            this.ground.add(new Coordinate(this.fieldHeight, i));
        }
    }

    public void generateObstacle(){
        this.ground.add(new Coordinate(this.fieldHeight, this.fieldWidth - 1));
        double random = Math.random();
        if(random <= 0.05) { // probability to add a big palm
            this.palm.add(new Coordinate(this.fieldHeight - 1, this.fieldWidth - 1));
            this.palm.add(new Coordinate(this.fieldHeight - 2, this.fieldWidth - 1));
        }
        else if(random > 0.1 && random <= 0.15) { //probability to add a little palm
            this.palm.add(new Coordinate(this.fieldHeight - 1, this.fieldWidth - 1));
        }
        else if(random > 0.8 && random <= 0.81) { //probability to add a bird
            int randomHeight = ThreadLocalRandom.current().nextInt(3, this.fieldHeight - 1);
            this.bird.add(new Coordinate(randomHeight, this.fieldWidth - 1));
        }
    }

    public void moveObstacle(){
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

    public void deleteOutOfViewObstacle() {
        if(this.palm.size() > 0 && this.palm.get(0).getY() < 0)
            this.palm.remove(0);
        if(this.bird.size() > 0 && this.bird.get(0).getY() < 0)
            this.bird.remove(0);
        if(this.ground.size() > 0 && this.ground.get(0).getY() < 0)
            this.ground.remove(0);
    }

    public void calculateScoreFromObstacle() {
        if (this.isInGame) {
            if (this.palm.contains(new Coordinate(this.fieldHeight - 1, 4)))
                this.score += 10;
            if (this.palm.contains(new Coordinate(this.fieldHeight - 2, 4)))
                this.score += 10;
        }
    }

    public int increaseSpeedAccordingToPoints() {
        if (this.score < 100)
            return 1000;
        else if (this.score < 200)
            return 500;
        else return 250;
    }
}